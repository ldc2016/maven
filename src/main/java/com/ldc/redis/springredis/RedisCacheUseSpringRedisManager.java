package com.ldc.redis.springredis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public class RedisCacheUseSpringRedisManager extends AbstractCacheManager{

	private final Log logger = LogFactory.getLog(RedisCacheManager.class);

    @SuppressWarnings("rawtypes")
    private final RedisTemplate template;
    private boolean usePrefix = false;
    private RedisCachePrefix cachePrefix = new DefaultRedisCachePrefix();
    private boolean dynamic = true;
    private long defaultExpiration = 0;
    private String redisKeyPrefix;

    @SuppressWarnings("rawtypes")
    public RedisCacheUseSpringRedisManager(RedisTemplate template) {
        this(template, Collections.<String> emptyList());
    }

    /**
     * managing caches for the specified cache names only.
     * @param template
     * @param cacheNames
     * @since 1.2
     */
    @SuppressWarnings("rawtypes")
    public RedisCacheUseSpringRedisManager(RedisTemplate template, Collection<String> cacheNames) {
        this.template = template;
        setCacheNames(cacheNames);
    }

    @SuppressWarnings("rawtypes")
    public RedisCacheUseSpringRedisManager(RedisTemplate template, Collection<String> cacheNames, boolean usePrefix,
                             String redisKeyPrefix, long defaultExpiration) {
        this.template = template;
        this.usePrefix = usePrefix;
        this.redisKeyPrefix = redisKeyPrefix;
        this.defaultExpiration = defaultExpiration;
        setCacheNames(cacheNames);
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if (cache == null && this.dynamic) {
            return createAndAddCache(name);
        }
        return cache;
    }

    /**
     * Specify the set of cache names for this CacheManager's 'static' mode. <br/>
     * The number of caches and their names will be fixed after a call to this method
     */
    public void setCacheNames(Collection<String> cacheNames) {

        if (!CollectionUtils.isEmpty(cacheNames)) {
            for (String cacheName : cacheNames) {
                createAndAddCache(cacheName);
            }
            this.dynamic = false;
        }
    }

    public void setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    public void setCachePrefix(RedisCachePrefix cachePrefix) {
        this.cachePrefix = cachePrefix;
    }

    public void setDefaultExpiration(long defaultExpireTime) {
        this.defaultExpiration = defaultExpireTime;
    }

    public String getKeyPrefix() {
        return redisKeyPrefix;
    }

    public void setKeyPrefix(String redisKeyPrefix) {
        this.redisKeyPrefix = redisKeyPrefix;
    }

	/*
	 * @see org.springframework.cache.support.AbstractCacheManager#loadCaches()
	 */
    @Override
    protected Collection<? extends Cache> loadCaches() {

        Assert.notNull(this.template, "A redis template is required in order to interact with data store");
        return addConfiguredCachesIfNecessary(Collections.<Cache> emptyList());
    }

    /**
     * Returns a new {@link Collection} of {@link Cache} from the given caches collection and adds the configured
     * {@link Cache}s of they are not already present.
     *
     * @param caches must not be {@literal null}
     * @return
     */
    private Collection<? extends Cache> addConfiguredCachesIfNecessary(Collection<? extends Cache> caches) {

        Assert.notNull(caches, "Caches must not be null!");

        Collection<Cache> result = new ArrayList<Cache>(caches);

        for (String cacheName : getCacheNames()) {

            boolean configuredCacheAlreadyPresent = false;

            for (Cache cache : caches) {

                if (cache.getName().equals(cacheName)) {
                    configuredCacheAlreadyPresent = true;
                    break;
                }
            }

            if (!configuredCacheAlreadyPresent) {
                result.add(getCache(cacheName));
            }
        }

        return result;
    }

    private Cache createAndAddCache(String cacheName) {
        addCache(createCache(cacheName));
        return super.getCache(cacheName);
    }

    @SuppressWarnings("unchecked")
    private RedisCacheUseSpringRedis createCache(String cacheName) {
        long expiration = computeExpiration(cacheName);
        //return new RedisCache(cacheName, (usePrefix ? cachePrefix.prefix(cacheName) : null), template, expiration);
        return new RedisCacheUseSpringRedis(cacheName, (usePrefix ? cachePrefix.prefix(redisKeyPrefix) : null), template, expiration);
    }

    private long computeExpiration(String name) {
        return defaultExpiration;
    }

}
