package com.github.bannirui.ekko.resp;

import com.github.bannirui.ekko.bean.ImServerNode;

/**
 * 用户登陆.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class LoginResp {

    private long opCode; // 操作状态码
    private ImServerNode server; // im server

    public LoginResp(long opCode) {
        this.opCode = opCode;
    }

    public long getOpCode() {
        return opCode;
    }

    public void setOpCode(long opCode) {
        this.opCode = opCode;
    }

    public ImServerNode getServer() {
        return server;
    }

    public void setServer(ImServerNode server) {
        this.server = server;
    }
}
