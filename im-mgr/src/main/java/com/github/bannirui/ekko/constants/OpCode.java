package com.github.bannirui.ekko.constants;

/**
 * 操作状态码.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public interface OpCode {

    long SUCC = 0L;
    long FAIL = -1L;

    // 参数不符合校验
    long PARAM_INVALID = 1L;

    /**
     * 用户相关.
     */
    interface User {

        // 用户不存在
        long NOT_EXIST = 1L << 1;

        // 非注册用户
        long NOT_REGISTER = 1L << 2;

        // 未登陆
        long NOT_LOGIN = 1L << 3;

        // 重复登陆
        long RE_LOGIN = 1L << 4;
    }

    /**
     * im server相关.
     */
    interface Peer {

        // 服务器不存在
        long NOT_EXIST = 1L << 1;

        // 服务器已经在线
        long ALREADY_ONLINE = 1L << 1;
    }
}
