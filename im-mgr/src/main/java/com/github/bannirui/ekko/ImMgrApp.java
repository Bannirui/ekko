package com.github.bannirui.ekko;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ${END}
 *
 * @author dingrui
 * @since 2023/4/19
 */
@SpringBootApplication
@EnableDubbo
public class ImMgrApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ImMgrApp.class);

    public static void main(String[] args) {
        SpringApplication.run(ImMgrApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("[IM-MGR] 启动成功.");
    }
}