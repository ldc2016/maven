package com.vip.redis.springredis;

import java.util.concurrent.TimeUnit;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public interface RedisApiUseSpringRedisTemplateManager {
    public Object get(String key) throws Exception;
    public <T> T getObject(String key, Class<T> clazz) throws Exception;

    public void set(String key, String value) throws Exception;
    public void set(String key, String value, long timeOut, TimeUnit timeUnit) throws Exception;

    public <T> void setObject(String key, T value, long timeOut, TimeUnit timeUnit) throws Exception;
    public <T> T getAndSet(String key, T value) throws Exception;

    public boolean setnx(String key, Object value) throws Exception;
    public void expire(String key, long times, TimeUnit timeUnit) throws Exception;
    public void del(String key) throws Exception;
    public void unLock(String key) throws Exception;
    public boolean lock(String key, long maxMillis) throws Exception;
}
