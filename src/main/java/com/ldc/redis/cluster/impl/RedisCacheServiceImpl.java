package com.ldc.redis.cluster.impl;

import com.alibaba.fastjson.JSON;
import com.ldc.redis.cluster.RedisApiByJedisClientManager;
import com.ldc.redis.cluster.redisCacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
@Service
public class RedisCacheServiceImpl implements redisCacheService {
    private Map<String,RedisApiByJedisClientManager> redisCacheMap = Collections.emptyMap();

    @Override
    public String get(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).get(key);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        T javaObject = JSON.parseObject(value, clazz);
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

    @Override
    public String set(String key, String value) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).set(key,value);
    }

    @Override
    public String set(String key, String value, long time) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).set(key,value,time);
    }

    @Override
    public Long del(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).del(key);
    }

    @Override
    public Long expire(String key, int seconds) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).expire(key, seconds);
    }

    @Override
    public Long setnx(String key, String value) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).setnx(key, value);
    }

    @Override
    public String getSet(String key, String value) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return getRedisCacheManager(key).getSet(key, value);
    }

    @Override
    public void init() {

    }

    public void setRedisCacheMap(Map<String, RedisApiByJedisClientManager> cacheManagerMap) {
        this.redisCacheMap = cacheManagerMap;
    }

    @Override
    public RedisApiByJedisClientManager getRedisCacheManager(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return redisCacheMap.get(Math.abs(key.hashCode()%redisCacheMap.size())+"");
    }
}
