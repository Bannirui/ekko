package com.github.bannirui.ekko.dal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bannirui.ekko.common.ex.BizException;
import com.github.bannirui.ekko.common.util.RedisUtil;
import com.github.bannirui.ekko.constants.OpCode;
import com.github.bannirui.ekko.constants.RedisKeyMGr;
import com.github.bannirui.ekko.dal.mapper.UserDao;
import com.github.bannirui.ekko.dal.model.User;
import com.github.bannirui.ekko.dal.service.UserService;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * {@link User}.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Service
@CacheConfig(cacheNames = "user", keyGenerator = "keyGenerator")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private UserServiceImpl self;

    @Cacheable()
    public String uname(Long uid) {
        User ret = null;
        if (Objects.isNull(uid) || Objects.isNull(ret = this.userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUid, uid)))) {
            return null;
        }
        return ret.getNickname();
    }

    @CachePut(key = "'login_'+#uid+'_'+#uname")
    @Override
    public long login(Long uid, String uname) {
        if (Objects.isNull(uid) || Objects.isNull(uname) || StringUtils.isBlank(uname)) {
            return OpCode.PARAM_INVALID;
        }
        String name = this.self.uname(uid);
        if (Objects.isNull(name)) {
            return OpCode.User.NOT_EXIST;
        }
        if (!StringUtils.equals(uname, name)) {
            return OpCode.User.NOT_REGISTER;
        }
        long status = this.self.loginCheck(uid, uname);
        if (status == OpCode.SUCC) {
            return OpCode.User.RE_LOGIN;
        }
        if (!RedisUtil.sSet(RedisKeyMGr.LOGIN_USER, uid)) {
            return OpCode.FAIL;
        }
        return OpCode.SUCC;
    }

    @Override
    public long logout(Long uid) {
        if (Objects.isNull(uid)) {
            return OpCode.PARAM_INVALID;
        }
        String uname = this.self.uname(uid);
        if (Objects.isNull(uname) || StringUtils.isBlank(uname)) {
            // 已登陆用户却没有注册信息 panic
            throw new BizException("登陆用户查无此人");
        }
        if (!RedisUtil.setRemove(RedisKeyMGr.LOGIN_USER, uid)) {
            return OpCode.FAIL;
        }
        this.self.loginClear(uid, uname);
        return OpCode.SUCC;
    }

    // 登陆用户信息查询
    @Cacheable(key = "'login_'+#uid+'_'+#uname")
    public long loginCheck(Long uid, String uname) {
        return OpCode.User.NOT_LOGIN;
    }

    // 登陆用户信息删除
    @CacheEvict(key = "'login_'+#uid+'_'+#uname")
    public void loginClear(Long uid, String uname) {

    }
}
