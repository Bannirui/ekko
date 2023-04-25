package com.github.bannirui.ekko.resp;

import com.github.bannirui.ekko.bean.ImServerNode;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登陆.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class LoginResp implements Serializable {

    @Serial
    private static final long serialVersionUID = -1187020628512758471L;

    private Long opCode; // 操作状态码
    private ImServerNode server; // im server

    public LoginResp(Long opCode) {
        this.opCode = opCode;
    }

    public Long getOpCode() {
        return opCode;
    }

    public void setOpCode(Long opCode) {
        this.opCode = opCode;
    }

    public ImServerNode getServer() {
        return server;
    }

    public void setServer(ImServerNode server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "LoginResp{"
            + "opCode=" + opCode
            + ", server=" + server
            + '}';
    }
}
