package com.github.bannirui.ekko.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 策略实现的统一入口. 不需要在每个实现类中开放 直接上移至基类.
 *
 * @author dingrui
 * @since 2023/4/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HandlerEntry {

}
