package com.github.bannirui.ekko.api;

import com.github.bannirui.ekko.req.LoginReq;
import com.github.bannirui.ekko.req.LogoutReq;
import com.github.bannirui.ekko.req.RegisterReq;
import com.github.bannirui.ekko.resp.LoginResp;

/**
 * 用户管理.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public interface UserServiceRpc {

    /**
     * 用户注册.
     */
    boolean register(RegisterReq req);

    /**
     * 登陆.
     */
    LoginResp login(LoginReq req);

    /**
     * 退出登陆.
     */
    boolean logout(LogoutReq req);
}
