package com.github.bannirui.ekko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static void main(String[] args) {
        SpringApplication.run(ImServerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("[IM-SERVER] 启动成功");
    }
}
