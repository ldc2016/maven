package com.vip.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public class RedisUtil {

    //访问密码
    private static String             PASSWORD       = null;
    /**如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态将是(耗尽)*/
    private static int                ACTIVE_MAX_COUNT     = 500;

    /**控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8*/
    private static int                IDLE_MAX_COUNT       = 200;

    /**等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException*/
    private static int                WAIT_MAX_TIME       = 1000;

    private static int                MAX_TIME_OUT        = 1000;

    /**在从redispool中获取一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的*/
    private static boolean            TEST_ON_BORROW = true;

    public static volatile Map<String,JedisPool> REDIS_CONNECTION_POOL = new ConcurrentHashMap<>();

    /**
     * 初始化Redis连接池
     */
    public static JedisPool getJedisPoolInstance(String redisHost,int redisPort) {
        // 从内存中获取redisPool连接实例
        try {
            if(REDIS_CONNECTION_POOL.get(redisHost+redisPort)!=null){
                return REDIS_CONNECTION_POOL.get(redisHost+redisPort);
            }

            // 若内存中不存在则初始化
            synchronized (RedisUtil.class) {
                if (REDIS_CONNECTION_POOL.get(redisHost+redisPort) == null) {
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(ACTIVE_MAX_COUNT);
                    config.setMaxIdle(IDLE_MAX_COUNT);
                    config.setMaxWaitMillis(WAIT_MAX_TIME);
                    config.setTestOnBorrow(TEST_ON_BORROW);
                    REDIS_CONNECTION_POOL.put(redisHost+redisPort,new JedisPool(config, redisHost, redisPort, MAX_TIME_OUT, PASSWORD));
                }
            }

            return REDIS_CONNECTION_POOL.get(redisHost+redisPort);

        } catch (Exception e) {
            throw new RuntimeException("=====> can not get jedisPool",e);
        }
    }

    public static JedisPool getJedisPoolInstance(String redisHost,int redisPort,int maxActive,int maxIdle,int maxWait,boolean testOnBorrow,int timeOut) {
        try {
            if(REDIS_CONNECTION_POOL.get(redisHost+redisPort)!=null){
                return REDIS_CONNECTION_POOL.get(redisHost+redisPort);
            }

            synchronized (RedisUtil.class) {
                if (REDIS_CONNECTION_POOL.get(redisHost+redisPort) == null) {
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(maxActive);
                    config.setMaxIdle(maxIdle);
                    config.setMaxWaitMillis(maxWait);
                    config.setTestOnBorrow(testOnBorrow);
                    REDIS_CONNECTION_POOL.put(redisHost+redisPort,new JedisPool(config, redisHost, redisPort, timeOut, PASSWORD));
                }
            }

            return REDIS_CONNECTION_POOL.get(redisHost+redisPort);

        } catch (Exception e) {
            throw new RuntimeException("====>can not get jedisPool",e);
        }
    }
}
