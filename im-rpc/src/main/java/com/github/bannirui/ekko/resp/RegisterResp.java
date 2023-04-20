package com.github.bannirui.ekko.resp;

/**
 * 注册响应.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class RegisterResp {

    private long uid; // 用户uid

    public RegisterResp(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
