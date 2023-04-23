package com.github.bannirui.ekko.constants;

/**
 * redis键.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public interface RedisKeyMGr {

    String PEER = "peer";
    String ONLINE_PEER = "online";

    interface User {

        String USER = "user"; // ttl
        String ALREADY_REGISTER = "already_register"; // ttl
        String ALREADY_LOGIN = "already_login"; // ttl=-1
        String ALREADY_LOGIN_SET = "already_login_set"; // ttl=-1
    }
}
