package com.github.bannirui.ekko.dal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.bannirui.ekko.dal.mapper.UserDao;
import com.github.bannirui.ekko.dal.model.User;
import com.github.bannirui.ekko.dal.service.UserService;
import org.springframework.stereotype.Service;

/**
 * {@link User}.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
