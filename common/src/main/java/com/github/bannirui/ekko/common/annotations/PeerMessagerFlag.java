package com.github.bannirui.ekko.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

/**
 * 服务端消息处理策略.
 *
 * @author dingrui
 * @see com.github.bannirui.ekko.bean.constants.MessageType
 * @since 2023/4/25
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PeerMessagerFlag {

    /**
     * 标识策略实现.
     *
     * @see com.github.bannirui.ekko.bean.constants.MessageType
     */
    long type();
}
