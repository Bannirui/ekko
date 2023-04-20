package com.github.bannirui.ekko.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bannirui.ekko.dal.mapper.UserDao;
import com.github.bannirui.ekko.dal.model.User;
import com.github.bannirui.ekko.dal.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * {@link User}.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Service
@CacheConfig(cacheNames = "UserServiceImpl", keyGenerator = "keyGenerator")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Cacheable(key = "#uid")
    @Override
    public User test(long uid) {
        return new User(1L, "dingrui");
    }
}
