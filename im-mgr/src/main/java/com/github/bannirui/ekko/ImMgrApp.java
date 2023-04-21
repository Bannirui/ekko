package com.github.bannirui.ekko;

import com.github.bannirui.ekko.zk.ZkWatcherBoot;
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
public class ImMgrApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ImMgrApp.class);

    @Value("${im-mgr.im-server.registry.zk.host}")
    private String zkHost;

    @Value("${im-mgr.im-server.registry.zk.port}")
    private int zkPort;

    @Value("${im-mgr.im-server.registry.zk.root}")
    private String zkRoot;

    public static void main(String[] args) {
        SpringApplication.run(ImMgrApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread t = new Thread(new ZkWatcherBoot(this.zkHost, this.zkPort, this.zkRoot), "im-mgr");
        t.setDaemon(true);
        t.start();
        LOG.info("[IM-MGR] 启动成功");
    }
}