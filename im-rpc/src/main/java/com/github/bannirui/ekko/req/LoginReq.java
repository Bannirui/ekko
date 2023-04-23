package com.github.bannirui.ekko.req;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登陆.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class LoginReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -6037016065242435365L;

    private Long uid;
    private String uname;

    public LoginReq(Long uid, String uname) {
        this.uid = uid;
        this.uname = uname;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
