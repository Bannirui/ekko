package com.github.bannirui.ekko.controller;

import com.github.bannirui.ekko.api.UserServiceRpc;
import com.github.bannirui.ekko.req.RegisterReq;
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
}
