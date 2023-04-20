package com.github.bannirui.ekko.starter;

import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis and cache.
 *
 * @author dingrui
 * @since 2023/4/20
 */
@Configuration
@EnableCaching
public class RedisCacheStarterConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration rc = RedisCacheConfiguration.defaultCacheConfig();
        rc = rc
            // key采用string序列化
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))
            // value采用jackson序列化
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))
            // value不缓存
            .disableCachingNullValues()
            // 缓存空间名称前缀
            .prefixCacheNameWith("im:")
            // 全局TTL
            .entryTtl(Duration.ofMinutes(30L));
        return RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(rc)
            .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        /**
         * @param target 代理的类
         * @param method 代理的方法
         * @param params 代理的方法参数
         */
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName()).append(":");
            sb.append(method.getName()).append(":");
            Object key = SimpleKeyGenerator.generateKey(params);
            return sb.append(key);
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = this.serializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key使用string序列化
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        // value使用jackson序列化
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    private GenericJackson2JsonRedisSerializer serializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
