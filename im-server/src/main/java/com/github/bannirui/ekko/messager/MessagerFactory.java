package com.github.bannirui.ekko.messager;

import com.github.bannirui.ekko.common.annotations.HandlerEntry;
import com.github.bannirui.ekko.common.annotations.PeerMessagerFlag;
import com.github.bannirui.ekko.common.util.SpringCtxUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 策略工厂.
 *
 * @author dingrui
 * @since 2023/4/25
 */
@Configuration
public class MessagerFactory {

    @Bean
    @DependsOn({SpringCtxUtil.BEAN_NAME})
    public MessageHandler messageHandler() {
        List<Object> beans = SpringCtxUtil.getBeansWithAnnotation(PeerMessagerFlag.class);
        if (CollectionUtils.isEmpty(beans)) {
            return null;
        }
        Map<Long, Function<MessageHandlerArgs, Long>> strategyHandlers = new HashMap<>();
        for (Object bean : beans) {
            if (Objects.isNull(bean)) {
                continue;
            }
            PeerMessagerFlag annotation = AnnotationUtils.findAnnotation(bean.getClass(), PeerMessagerFlag.class);
            if (Objects.isNull(annotation)) {
                continue;
            }
            long flag = annotation.type(); // 注解方法
            for (Method m : bean.getClass().getMethods()) {
                if (m.isAnnotationPresent(HandlerEntry.class)) { // 策略实现入口
                    strategyHandlers.put(flag, args -> {
                        try {
                            return (Long) (m.invoke(bean, args));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
                }
            }
        }
        return (flag, arg) -> (Objects.isNull(flag) || MapUtils.isEmpty(strategyHandlers) || !strategyHandlers.containsKey(flag)) ? null
            : strategyHandlers.get(flag).apply(arg);
    }
}
