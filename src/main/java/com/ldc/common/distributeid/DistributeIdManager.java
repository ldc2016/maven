package com.ldc.common.distributeid;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public interface DistributeIdManager {
    /**
     * 获取流水号
     */
    public String getUniqueDistributeIdByIdKeyName(String idKeyName, String userUid) throws Exception;

}
