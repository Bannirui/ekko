package com.github.bannirui.ekko.req;

/**
 * 注册请求.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class RegisterReq {

    private String uname; // 昵称

    public RegisterReq(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
