package com.github.bannirui.ekko;

import com.github.bannirui.ekko.api.Hello;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author dingrui
 * @since 2023/4/19
 */
@DubboService
public class Impl implements Hello {

    @Override
    public String sayHi() {
        return "Here is provider";
    }
}
