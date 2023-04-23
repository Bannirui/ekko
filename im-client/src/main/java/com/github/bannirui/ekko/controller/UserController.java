package com.github.bannirui.ekko.controller;

import com.github.bannirui.ekko.api.UserServiceRpc;
import com.github.bannirui.ekko.bean.constants.OpCode;
import com.github.bannirui.ekko.bean.resp.LoginResponse;
import com.github.bannirui.ekko.req.LoginReq;
import com.github.bannirui.ekko.req.LogoutReq;
import com.github.bannirui.ekko.req.RegisterReq;
import com.github.bannirui.ekko.resp.LoginResp;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户.
 *
 * @author dingrui
 * @since 2023/4/23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference(url = "dubbo://localhost:20882")
    private UserServiceRpc userServiceRpc;

    /**
     * 注册新用户.
     *
     * @param nickName 昵称
     * @return uid 注册成功用户的uid
     */
    @PostMapping("/register")
    public ResponseEntity<Long> register(String nickName) {
        if (Objects.isNull(nickName) || StringUtils.isBlank(nickName)) {
            return ResponseEntity.ok(null);
        }
        long uid = System.currentTimeMillis();
        boolean succ = this.userServiceRpc.register(new RegisterReq(uid, nickName));
        return succ ? ResponseEntity.ok(uid) : ResponseEntity.ok(null);
    }

    /**
     * 用户登陆.
     *
     * @param uname 昵称
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(Long uid, String uname) {
        if (Objects.isNull(uid) || Objects.isNull(uname) || StringUtils.isBlank(uname)) {
            return ResponseEntity.ok(new LoginResponse(OpCode.PARAM_INVALID));
        }
        LoginResp ret = this.userServiceRpc.login(new LoginReq(uid, uname));
        if (Objects.isNull(ret)) {
            return ResponseEntity.ok(new LoginResponse(OpCode.FAIL));
        }
        if (ret.getOpCode() == OpCode.SUCC) {
            return ResponseEntity.ok(new LoginResponse(ret.getOpCode(), ret.getServer().getHost(), ret.getServer().getImPort()));
        }
        return ResponseEntity.ok(new LoginResponse(ret.getOpCode()));
    }

    /**
     * 退出登陆.
     */
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(Long uid) {
        if (Objects.isNull(uid)) {
            return ResponseEntity.ok(false);
        }
        boolean logout = this.userServiceRpc.logout(new LogoutReq(uid));
        return ResponseEntity.ok(logout);
    }
}
