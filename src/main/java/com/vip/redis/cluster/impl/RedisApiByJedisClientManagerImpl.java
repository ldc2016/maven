package com.vip.redis.cluster.impl;

import com.alibaba.fastjson.JSON;
import com.vip.redis.RedisUtil;
import com.vip.redis.cluster.RedisApiByJedisClientManager;
import com.vip.smart.framework.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
@Service
public class RedisApiByJedisClientManagerImpl implements RedisApiByJedisClientManager {

    private final Logger logger = LoggerFactory.getLogger(RedisApiByJedisClientManagerImpl.class);

    protected String redisHost=null;//缓存host
    protected int  redisPort=6379;  //缓存端口
    protected int maxActive=500;    //最大活跃数
    protected int maxIdle=200;      //最大空闲数
    protected int maxWait=1000;     //最大等待时间 毫秒
    protected boolean testOnBorrow=true;// 获取前是否检测
    protected int timeOut=1000;    //超时时间

    private JedisPool jedisPool=null;


    public String get(String key){
        Jedis jedis=null;
        String returnStr=null;
        try{
            jedis=jedisPool.getResource();
            returnStr= jedis.get(key);
        }catch (Exception e){
            logger.error(" get the value of key:{} from redis happen error!" ,key, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnStr;
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        T javaObject = JSON.parseObject(value,clazz);
        if (javaObject == null) {
            return null;
        }
        return javaObject;
    }

    @Override
    public <T> String setObject(String key, T value, long time) {
        if(value==null){
            return null;
        }
        String valueStr = JSON.toJSONString(value);
        return set(key,valueStr,time);
    }

    public String set(String key,String value){

        Jedis jedis=null;
        String returnStr=null;
        try{
            jedis=jedisPool.getResource();
            returnStr= jedis.set(key, value);
        }catch (Exception e){
            logger.error(" get the value of key:{} from redis happen error!" ,key, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }

        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnStr;
    }

    public String set(String key,String value,long time){
        Jedis jedis=null;
        String returnStr=null;
        try{
            jedis=jedisPool.getResource();
            returnStr= jedis.set(key, value, "NX", "EX", time);
        }catch (Exception e){
            logger.error(" set the value:{} <-> key:{} in redis expireTime: {}!" ,value ,key,time, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }

        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnStr;
    }

    public Long del(String key){

        Jedis jedis=null;
        Long returnLong=null;
        try{
            jedis=jedisPool.getResource();
            returnLong= jedis.del(key);
        }catch (Exception e){
            logger.error(" del the value of key:{} from redis happen error!" ,key, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }

        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnLong;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis=null;
        Long returnLong=null;
        try{
            jedis=jedisPool.getResource();
            returnLong= jedis.expire(key, seconds);
        }catch (Exception e){
            logger.error("redis_expire error,key:" + key, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }

        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnLong;
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis=null;
        Long returnLong=null;
        try{
            jedis=jedisPool.getResource();
            returnLong= jedis.setnx(key, value);
        }catch (Exception e){
            logger.error("redis_setnx error,key:" + key, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }

        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnLong;
    }

    @Override
    public String getSet(String key, String value) {
        Jedis jedis=null;
        String returnStr=null;
        try{
            jedis=jedisPool.getResource();
            returnStr= jedis.getSet(key, value);
        }catch (Exception e){
            logger.error("redis_getSet error,key:" + key + " value:" + value, e);
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }

        }finally {
            if(jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return returnStr;
    }

    @Override
    public void init() {
        try{
            jedisPool= RedisUtil.getJedisPoolInstance(redisHost,redisPort,maxActive,maxIdle,maxWait,testOnBorrow,timeOut);
        }catch (Exception e){
            logger.error("init jedisPool happen error",e);
        }
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }


}
