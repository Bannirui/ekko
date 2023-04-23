package com.github.bannirui.ekko.req;

import java.io.Serial;
import java.io.Serializable;

/**
 * 退出登陆.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class LogoutReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -4674694880540840657L;

    private Long uid;

    public LogoutReq(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
