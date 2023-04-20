package com.github.bannirui.ekko.req;

/**
 * 退出登陆.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class LogoutReq {

    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
