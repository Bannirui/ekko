package com.github.bannirui.ekko.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingrui
 * @since 2023/4/19
 */
@Configuration
@EnableDubbo(scanBasePackages = {"com.github.bannirui.ekko"})
public class DubboStarterConfig {

}
