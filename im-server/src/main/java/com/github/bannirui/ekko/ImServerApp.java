package com.github.bannirui.ekko;

import com.github.bannirui.ekko.zk.ZkRegistryBoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main.
 *
 * @author dingrui
 * @since 2023/4/19
 */
@SpringBootApplication
public class ImServerApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ImServerApp.class);

    @Value("${im-server.registry.zk.host}")
    private String zkHost;

    @Value("${im-server.registry.zk.port}")
    private int zkPort;

    @Value("${im-server.registry.zk.root}")
    private String zkRoot;

    @Value("${im-server.im-port}")
    private int imPort;

    public static void main(String[] args) {
        SpringApplication.run(ImServerApp.class, args);
    }

    /**
     * 将当前服务器信息写到zk临时节点. 形如 /im-peers/ip:im-port
     */
    @Override
    public void run(String... args) throws Exception {
        LOG.info("[IM-SERVER] 启动成功");
        Thread thread = new Thread(new ZkRegistryBoot(this.zkHost, this.zkPort, this.zkRoot, this.imPort), "im-server-registry");
        thread.setDaemon(true);
        thread.start();
    }
}
