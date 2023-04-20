package com.github.bannirui.ekko.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 启动器.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Configuration
@MapperScan("com.github.bannirui.ekko.dal.mapper")
public class MybatisplusStarterConfig {

}
