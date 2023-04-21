package com.github.bannirui.ekko.service;

/**
 * 通信状态检测.
 * <ul>
 *     <li>服务端没收到客户端心跳</li>
 * </ul>
 *
 * @author dingrui
 * @since 2023/4/21
 */
public interface HealthService {

    void process();
}
