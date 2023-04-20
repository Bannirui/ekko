package com.github.bannirui.ekko.dal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.bannirui.ekko.dal.model.User;

/**
 * {@link User}.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public interface UserService extends IService<User> {

    /**
     * 用户登陆.
     * <ul>在redis中存储两个数据类型
     * <li>string 用于标识单用户是否在线 值就是该方法的返回值</li>
     * <li>set 用于统计在线用户</li>
     * </ul>
     *
     * @return 操作状态码 {@link com.github.bannirui.ekko.constants.OpCode}
     */
    long login(Long uid, String uname);

    /**
     * 退出登录.
     *
     * @return 操作状态码. {@link com.github.bannirui.ekko.constants.OpCode}
     */
    long logout(Long uid);
}
