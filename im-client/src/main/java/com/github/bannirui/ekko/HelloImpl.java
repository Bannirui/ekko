package com.github.bannirui.ekko;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author dingrui
 * @since 2023/4/19
 */
@Component
public class HelloImpl implements CommandLineRunner {

    @DubboReference(check = false)

    @Override
    public void run(String... args) throws Exception {
    }
}
