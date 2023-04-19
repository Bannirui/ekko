package com.github.bannirui.ekko;

import com.github.bannirui.ekko.api.Hello;
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
    private Hello hello;

    @Override
    public void run(String... args) throws Exception {
        String ret = this.hello.sayHi();
        System.out.println("[CONSUMER] 调用rpc结果为: " + ret);
    }
}
