package com.github.bannirui.ekko.req;

/**
 * 用户登陆.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class LoginReq {

    private long uid;
    private String uname;

    public LoginReq(long uid, String uname) {
        this.uid = uid;
        this.uname = uname;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
