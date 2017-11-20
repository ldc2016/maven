package com.ldc.common.distributeid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public abstract class AbstractDistributedIdManager {

    public static final Integer           CACHE_DEFAULT_INCREMENT_STEP  = 100;
    public static final Integer           DEFAULT_REPETION_TIMES  = 2;

    private final Map<String, AtomicLong> currDistributeIdMap  = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> finalIdValueMap      = new ConcurrentHashMap<>();
    public Map<String, Integer>           failedCountMap       = new ConcurrentHashMap<>();   //记录某个key的错误次数,抛出异常后清零
    public Integer                        scanStep           = CACHE_DEFAULT_INCREMENT_STEP; // 步长，一次取出
    public Integer                        retryCount          = DEFAULT_REPETION_TIMES;       //重试次数:从db获取当前序列值


    public Integer getScanStep() {
        return scanStep;
    }

    public void setScanStep(Integer scanStep) {
        this.scanStep = scanStep;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * 重新初始化给定idKeyName的值，子类自己实现
     */
    public abstract Long reinitializeIdValue(String idKeyName);


    /**
     *初始化给定的idKeyName的idValue
     */
    private void initializeIdValue(Long idValue, String idKeyName) {
        if (currDistributeIdMap.get(idKeyName) == null) {
            currDistributeIdMap.put(idKeyName, new AtomicLong());
            finalIdValueMap.put(idKeyName, new AtomicLong());
        }
        currDistributeIdMap.get(idKeyName).set(idValue);

        finalIdValueMap.get(idKeyName).set(idValue);
        finalIdValueMap.get(idKeyName).getAndAdd(getScanStep());
        failedCountMap.put(idKeyName,0);
    }

    /**
     * 可以直接调用
     * @param idKeyName 名称
     * @return idValue 值
     */
    public Long getUniqueDistributeId(String idKeyName) {
        if (currDistributeIdMap.get(idKeyName) == null) {
            initializeIdValue(reinitializeIdValue(idKeyName), idKeyName);
        }

        if (currDistributeIdMap.get(idKeyName).longValue() < finalIdValueMap.get(idKeyName).longValue()) {
            return currDistributeIdMap.get(idKeyName).getAndIncrement();
        }

        try {
            initializeIdValue(reinitializeIdValue(idKeyName), idKeyName);//重新初始化不可能失败，直接清零
            failedCountMap.put(idKeyName,0);
            return currDistributeIdMap.get(idKeyName).getAndIncrement();
        } catch (Throwable e) {
            if ((failedCountMap.get(idKeyName)+1) > getRetryCount()) {     //如果已经大于重试次数，则清零当前错误次数
                failedCountMap.put(idKeyName,0);
                throw new RuntimeException("[AbstractDistributedIdManager.getUniqueDistributeId]error,idKeyName:"+idKeyName, e);
            }else {
                failedCountMap.put(idKeyName,failedCountMap.get(idKeyName)+1);
            }

            return getUniqueDistributeId(idKeyName);
        }
    }



}
