package com.github.bannirui.ekko.dal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.common.ex.BizException;
import com.github.bannirui.ekko.common.util.RedisUtil;
import com.github.bannirui.ekko.constants.RedisKeyMGr;
import com.github.bannirui.ekko.dal.mapper.UserDao;
import com.github.bannirui.ekko.dal.model.User;
import com.github.bannirui.ekko.dal.service.UserService;
import com.github.bannirui.ekko.starter.RedisCacheStarterConfig;
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
@CacheConfig(cacheNames = RedisKeyMGr.User.USER, keyGenerator = RedisCacheStarterConfig.KEY_GENERATOR)
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private UserServiceImpl self;

    @Override
    public long register(User user) {
        if (Objects.isNull(user) || Objects.isNull(user.getUid())) {
            return OpCode.PARAM_INVALID;
        }
        if (this.userDao.insert(user) != 1) {
            return OpCode.FAIL;
        }
        this.self.alreadyRegisterPut(user);
        return OpCode.SUCC;
    }

    @Override
    public long login(Long uid, String uname) {
        if (Objects.isNull(uid) || Objects.isNull(uname) || StringUtils.isBlank(uname)) {
            return OpCode.PARAM_INVALID;
        }
        // 重复登陆
        User loginUser = this.self.alreadyLoginLoad(uid);
        if (Objects.nonNull(loginUser)) {
            return OpCode.User.Login.RE_LOGIN;
        }
        // 还未注册
        User registerUser = this.self.alreadyRegisterLoad(uid);
        if (Objects.isNull(registerUser) || !StringUtils.equals(uname, registerUser.getNickname())) {
            return OpCode.User.Register.NOT_REGISTER;
        }
        // 登陆
        if (!RedisUtil.sSet(RedisKeyMGr.User.ALREADY_LOGIN_SET, uid)) {
            return OpCode.FAIL;
        }
        this.self.alreadyLoginPut(new User(uid, uname));
        return OpCode.SUCC;
    }

    @Override
    public long logout(Long uid) {
        if (Objects.isNull(uid)) {
            return OpCode.PARAM_INVALID;
        }
        if (!this.valid(uid)) {
            return OpCode.FAIL;
        }
        if (!RedisUtil.setRemove(RedisKeyMGr.User.ALREADY_LOGIN_SET, uid)) {
            return OpCode.FAIL;
        }
        this.self.alreadyLoginDel(uid);
        return OpCode.SUCC;
    }

    @Override
    public Long logoff(Long uid) {
        if (Objects.isNull(uid)) {
            return OpCode.PARAM_INVALID;
        }
        if (!this.valid(uid)) {
            return OpCode.FAIL;
        }
        this.self.alreadyLoginDel(uid);
        if (!RedisUtil.setRemove(RedisKeyMGr.User.ALREADY_LOGIN_SET, uid)) {
            return OpCode.FAIL;
        }
        this.userDao.delete(new LambdaQueryWrapper<User>().eq(User::getUid, uid));
        this.self.alreadyRegisterDel(uid);
        return OpCode.SUCC;
    }

    /**
     * 缓存已注册用户.
     *
     * @see RedisKeyMGr.User#ALREADY_REGISTER
     */
    @CachePut(key = "'already_register_'+#u.uid")
    public User alreadyRegisterPut(User u) {
        return u;
    }

    /**
     * 加载已注册用户.
     *
     * @see RedisKeyMGr.User#ALREADY_REGISTER
     */
    @Cacheable(key = "'already_register_'+#uid")
    public User alreadyRegisterLoad(Long uid) {
        User ret = null;
        if (Objects.isNull(uid) || Objects.isNull(ret = this.userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUid, uid)))) {
            return null;
        }
        return ret;
    }

    /**
     * 删除已注册用户.
     *
     * @see RedisKeyMGr.User#ALREADY_REGISTER
     */
    @CacheEvict(key = "'already_register_'+#uid")
    public void alreadyRegisterDel(Long uid) {

    }


    /**
     * 缓存已登陆用户.
     *
     * @see RedisKeyMGr.User#ALREADY_LOGIN
     */
    @CachePut(cacheNames = RedisKeyMGr.User.ALREADY_LOGIN, key = "#u.uid")
    public User alreadyLoginPut(User u) {
        return u;
    }

    /**
     * 加载已登陆用户.
     *
     * @see RedisKeyMGr.User#ALREADY_LOGIN
     */
    @Cacheable(cacheNames = RedisKeyMGr.User.ALREADY_LOGIN, key = "#uid")
    public User alreadyLoginLoad(Long uid) {
        return null;
    }

    /**
     * 删除已登陆用户.
     *
     * @see RedisKeyMGr.User#ALREADY_LOGIN
     */
    @CacheEvict(cacheNames = RedisKeyMGr.User.ALREADY_LOGIN, key = "#uid")
    public void alreadyLoginDel(Long uid) {

    }

    /**
     * 用户状态验证. 用户状态前提注册过\登陆过.
     */
    private boolean valid(Long uid) {
        if (Objects.isNull(uid)) {
            return false;
        }
        User loginUser = this.self.alreadyLoginLoad(uid);
        String lname = null;
        if (Objects.isNull(loginUser) || Objects.isNull(lname = loginUser.getNickname()) || StringUtils.isBlank(lname)) {
            throw new BizException("非登陆用户");
        }
        User registerUser = this.self.alreadyRegisterLoad(uid);
        String rname = null;
        if (Objects.isNull(registerUser) || Objects.isNull(rname = registerUser.getNickname()) || StringUtils.isBlank(rname)) {
            throw new BizException("非注册用户");
        }
        if (!StringUtils.equals(lname, rname)) {
            return false;
        }
        return true;
    }
}
