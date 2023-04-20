package com.github.bannirui.ekko.common.util;

import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis.
 *
 * @author dingrui
 * @since 2023/4/20
 */
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate = SpringCtxUtil.getBean("redisTemplate", RedisTemplate.class);

    public static <T> Set<T> sGet(String key) {
        try {
            return ((Set<T>) redisTemplate.opsForSet().members(key));
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean setRemove(String key, Object value) {
        try {
            return redisTemplate.opsForSet().remove(key, value) == 1L;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean sSet(String key, Object value) {
        try {
            return redisTemplate.opsForSet().add(key, value) == 1L;
        } catch (Exception ignored) {
            return false;
        }
    }
}
