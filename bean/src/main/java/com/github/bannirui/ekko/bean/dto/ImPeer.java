package com.github.bannirui.ekko.bean.dto;

/**
 * im server.
 *
 * @author dingrui
 * @since 2023/4/21
 */
public class ImPeer {

    private String host;
    private int imPort;

    public ImPeer() {
    }

    public ImPeer(String host, int imPort) {
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
