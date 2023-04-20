package com.github.bannirui.ekko.bean;

/**
 * im server服务器.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class ImServerNode {

    private String host; // 域名\ip
    private int imPort; // im端口

    public ImServerNode(String host, int imPort) {
        this.host = host;
        this.imPort = imPort;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getImPort() {
        return imPort;
    }

    public void setImPort(int imPort) {
        this.imPort = imPort;
    }
}
