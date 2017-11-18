package com.vip.redis.cluster;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public interface RedisLockService {
    /**
     * 获得锁
     */
    boolean acquireLock(String key, long timeout);

    /**
     * 释放锁
     */
    void releaseLock();
}

