package com.vip.redis.cluster;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public interface redisCacheService extends RedisApiByJedisClientManager {
    RedisApiByJedisClientManager getRedisCacheManager(String key);
}
