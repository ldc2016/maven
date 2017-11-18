package com.vip.redis.springredis.impl;

import com.alibaba.fastjson.JSON;
import com.vip.redis.springredis.RedisApiUseSpringRedisTemplateManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
@Service
public class RedisApiUseSpringRedisTemplateManagerImpl implements RedisApiUseSpringRedisTemplateManager {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheManager cacheManager;

    private static RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() throws Exception{
        if (redisTemplate != null) {
            return redisTemplate;
        }
        /**从applicationContext中redis缓存*/
        Cache cache = cacheManager.getCache("");
        if (cache == null) {
            throw new Exception("cache-cluster 配置错误，请检查配置！");
        }
        redisTemplate = (RedisTemplate<String, String>) cache.getNativeCache();
        return redisTemplate;
    }

    public Object get(String key) throws Exception {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Object obj = getRedisTemplate().opsForValue().get(key);
        return obj;
    }

    public <T> T getObject(String key, Class<T> clazz) throws Exception{
        Object value = get(key);
        if (value == null) {
            return null;
        }
        T cacheObject = JSON.parseObject(String.valueOf(value), clazz);
        if (cacheObject == null) {
            return null;
        }
        return cacheObject;
    }

    public void set(String key, String value) throws Exception{
        getRedisTemplate().opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeOut, TimeUnit timeUnit) throws Exception{
        getRedisTemplate().opsForValue().set(key, value, timeOut, timeUnit);
    }

    public <T> void setObject(String key, T value, long timeOut, TimeUnit timeUnit) throws Exception {
        if (value == null) {
            return;
        }
        String valueStr = JSON.toJSONString(value);
        getRedisTemplate().opsForValue().set(key, valueStr, timeOut, timeUnit);
    }

    public <T> T getAndSet(String key, T value) throws Exception{
        return (T) getRedisTemplate().opsForValue().getAndSet(key, value);
    }

    public boolean setnx(String key, Object value) throws Exception{
        return getRedisTemplate().opsForValue().setIfAbsent(key, value);
    }

    public void expire(String key, long times, TimeUnit timeUnit) throws Exception {
        getRedisTemplate().expire(key, times, timeUnit);
    }

    public void del(String key) throws Exception {
        getRedisTemplate().delete(key);
    }

    /**
     * 解锁
     * @param key
     * @throws Exception
     */
    public void unLock(String key) throws Exception {
        long existsTimeout = getObject(key, Long.class) == null ? 0 : this.getObject(key, Long.class);
        if (existsTimeout > System.currentTimeMillis()) {
            del(key);
        }
    }

    /**
     * 获取锁
     * @param key
     * @param maxMillis
     * @return
     * @throws Exception
     */
    public boolean lock(String key, long maxMillis) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        long lockTimeout = currentTimeMillis + maxMillis + 1;
        boolean res = this.setnx(key, lockTimeout);
        if (!res) {
            Long existsTimeout = this.getObject(key, Long.class);
            if (existsTimeout == null) {
                return false;
            }
            if (existsTimeout < currentTimeMillis) {
                Long temp = getAndSet(key, lockTimeout);
                if (temp != null) {
                    return temp < currentTimeMillis;
                }
            }
        }
        return res;
    }
}
