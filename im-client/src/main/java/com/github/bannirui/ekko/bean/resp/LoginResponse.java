package com.github.bannirui.ekko.bean.resp;

/**
 * 登陆.
 *
 * @author dingrui
 * @since 2023/4/23
 */
public class LoginResponse {

    private Long opCode; // 状态标识
    private String host; // 域名\ip
    private Integer imPort; // im端口

    public LoginResponse(Long opCode) {
        this.opCode = opCode;
    }

    public LoginResponse(Long opCode, String host, Integer imPort) {
        this.opCode = opCode;
        this.host = host;
        this.imPort = imPort;
    }

    public Long getOpCode() {
        return opCode;
    }

    public void setOpCode(Long opCode) {
        this.opCode = opCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getImPort() {
        return imPort;
    }

    public void setImPort(Integer imPort) {
        this.imPort = imPort;
    }

    @Override
    public String toString() {
        return "LoginResponse{"
            + "opCode=" + opCode
            + ", host='" + host + '\''
            + ", imPort=" + imPort
            + '}';
    }
}
