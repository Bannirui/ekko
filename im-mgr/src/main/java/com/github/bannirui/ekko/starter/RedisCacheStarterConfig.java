package com.github.bannirui.ekko.starter;

import com.github.bannirui.ekko.constants.RedisKeyMGr.Peer;
import com.github.bannirui.ekko.constants.RedisKeyMGr.User;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
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

    public static final String KEY_GENERATOR = "keyGenerator";

    // 默认策略
    private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> map = new HashMap<>();
        // 按照分组级别 自定义ttl时间
        map.put(Peer.ALREADY_ONLINE, this.redisCacheConfigurationWithTTL(-1L)); // 在线的服务器
        map.put(User.ALREADY_LOGIN, this.redisCacheConfigurationWithTTL(-1L)); // 登陆的用户
        return map;
    }

    // 为指定key配置策略
    private RedisCacheConfiguration redisCacheConfigurationWithTTL(long seconds) {
        RedisCacheConfiguration rc = RedisCacheConfiguration.defaultCacheConfig();
        rc = rc
            // key采用string序列化
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))
            // value采用jackson序列化
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))
            // 缓存空间名称前缀
            .prefixCacheNameWith("im:")
            // 全局TTL
            .entryTtl(Duration.ofSeconds(seconds));
        return rc;
    }

    /**
     * 申明缓存管理器.
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
            RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
            this.redisCacheConfigurationWithTTL(600L), // 默认ttl策略
            this.redisCacheConfigurationMap() // 对指定的cache分组进行自定义ttl策略
        );
    }

    @Bean(name = {KEY_GENERATOR})
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
