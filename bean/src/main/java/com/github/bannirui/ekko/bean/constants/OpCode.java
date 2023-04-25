package com.github.bannirui.ekko.bean.constants;

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

        interface Register {

            // 非注册用户
            long NOT_REGISTER = 1L << 1;
        }

        interface Login {

            // 未登陆
            long NOT_LOGIN = 1L << 2;

            // 重复登陆
            long RE_LOGIN = 1L << 3;
        }
    }

    /**
     * im server相关.
     */
    interface Peer {

        // 服务器不存在
        long NOT_EXIST = 1L << 4;

        // 服务器已经在线
        long ALREADY_ONLINE = 1L << 5;
    }

    interface Sender {

        /**
         * 并未登陆成功.
         * <ul>登陆的语义是
         * <li>1 用户管理层面的登陆</li>
         * <li>2 成功初始化出netty的channel并且缓存在内存中</li>
         * </ul>
         */
        long LOGIN_FAIL = 1L << 6;
    }
}
