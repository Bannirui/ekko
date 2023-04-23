package com.github.bannirui.ekko.req;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注册请求.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class RegisterReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -7612644805888038065L;

    private Long uid; // uid
    private String uname; // 昵称

    public RegisterReq(Long uid, String uname) {
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
