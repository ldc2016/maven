package com.ldc.redis.cluster;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public interface RedisApiByJedisClientManager {

    /**
     * 获得缓存
     */
    String get(String key);


    /**
     * 获得缓存
     */
    <T> T getObject(String key, Class<T> clazz);


    /**
     * 设置缓存
     */

    <T> String setObject(String key, T value, long time) ;

    /**
     * 设置缓存，不超时
     */
    String set(String key, String value);

    /**
     * 设置缓存，超时时间单位为 秒
     */
    String set(String key, String value, long time);

    /**
     * 删除缓存
     */
    Long del(String key);

    public void init();

    Long expire(String key, int seconds);

    Long setnx(String key, String value);

    String getSet(String key, String value);

}
