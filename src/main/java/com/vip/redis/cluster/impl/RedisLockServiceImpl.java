package com.vip.redis.cluster.impl;

import com.vip.redis.cluster.RedisLockService;
import com.vip.redis.cluster.redisCacheService;
import com.vip.smart.framework.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
@Service
public class RedisLockServiceImpl implements RedisLockService {
    private Logger logger = LoggerFactory.getLogger(RedisLockServiceImpl.class);

    /** 纳秒转换基数 */
    private static final long MILLI_NANO_CONVERSION = 1000 * 1000 * 1000L;

    /**线程隔离容器*/
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Autowired
    private redisCacheService redisCacheManager;

    @Override
    public boolean acquireLock(final String key, final long timeout) {
        Assert.notNull(key, "key required");
        Assert.notNull(key, "timeout required");
        try {
            long nano = System.nanoTime();
            int expire = (int) timeout;
            while ((System.nanoTime() - nano) < timeout * MILLI_NANO_CONVERSION) {
                if (redisCacheManager.setnx(key, String.valueOf(System.nanoTime())) == 1) {
                    redisCacheManager.expire(key, expire);
                    threadLocal.set(key);
                    return true;
                }
                String s = redisCacheManager.get(key);
                if (s == null) {
                    continue;
                }
                logger.info("请求{}阻塞中...", key);
                TimeUnit.MILLISECONDS.sleep(100);
                if ((System.nanoTime() - Long.parseLong(s)) / MILLI_NANO_CONVERSION >= expire) {
                    redisCacheManager.del(key);
                }
            }
        } catch (Throwable e) {
            logger.error("acquire lock error : " + e.getMessage(), e);
            throw new RuntimeException("获取分布式锁失败！", e);
        }
        return false;
    }

    @Override
    public void releaseLock() {
        try {
            if (!StringUtils.isEmpty(threadLocal.get()) && redisCacheManager.del(threadLocal.get()) == 1) {
                threadLocal.remove();
            }
        } catch (Exception e) {
            logger.error("release lock error :" + e.getMessage(), e);
        }
    }
}
