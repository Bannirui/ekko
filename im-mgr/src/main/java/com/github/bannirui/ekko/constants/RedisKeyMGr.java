package com.github.bannirui.ekko.constants;

/**
 * redis键.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public interface RedisKeyMGr {

    interface Peer {

        String Peer = "peer"; // ttl
        String ALREADY_ONLINE = "already_online_peer"; // ttl=-1
        String ALREADY_ONLINE_SET = "already_online_peer_set"; // ttl=-1
    }

    interface User {

        String USER = "user"; // ttl
        String ALREADY_REGISTER = "already_register_user"; // ttl
        String ALREADY_LOGIN = "already_login_user"; // ttl=-1
        String ALREADY_LOGIN_SET = "already_login_user_set"; // ttl=-1
    }
}
