## REDIS+CACHE

### 1 指定序列化方式为jackson

* POJO一定要提供个无参构造方法给反序列化用
* @CacheConfig注解不指定keyGenerator策略
    * key=缓存全局配置的前缀+CacheConfig注解的cacheNames+::+Cacheable注解的key
* @CacheConfig注解指定keyGenerator策略
* 指定缓存组的方式
    * @CacheConfig注解的cacheNames方法
    * @Cacheable注解的value方法
    * 优先级是方法级的@Cacheable高于类级别的@CacheConfig
    * 
