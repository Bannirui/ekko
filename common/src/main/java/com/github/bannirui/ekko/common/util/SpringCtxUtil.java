package com.github.bannirui.ekko.common.util;

import java.util.Objects;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring bean.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Component(value = SpringCtxUtil.BEAN_NAME)
public class SpringCtxUtil implements ApplicationContextAware, DisposableBean {

    public static final String BEAN_NAME = "springCtxUtil";

    private static ApplicationContext ctx;

    @Override
    public void destroy() throws Exception {
        ctx = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        if (Objects.isNull(ctx)) {
            throw new IllegalStateException("ApplicationContext not exists.");
        }
        return ctx.getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        if (Objects.isNull(ctx)) {
            throw new IllegalStateException("ApplicationContext not exists.");
        }
        return ctx.getBean(name, requiredType);
    }
}
