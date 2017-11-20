package com.ldc.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by dacheng.liu on 2017/11/20.
 */
public class DistributeIdTools {

    public static String buildUniqueId(String uidFlag, String appSysCode, String channelCode, String extCode,
                               String idcFlag, String tbFlag, String idValue) {
        StringBuilder sb = new StringBuilder();
        sb.append(uidFlag);
        sb.append(idValue);
        sb.append(appSysCode);
        sb.append(channelCode);
        if (StringUtils.isNotBlank(extCode)) {
            sb.append(extCode);
        }
        sb.append(idcFlag);
        sb.append(tbFlag);
        return sb.toString();
    }

    /**
     * 构造流水号
     */
    public static String buildDistributeUniqueId(String dateTimeFlag, String channelCode,
                                    String zeroFlag, String uidFlag, String idValue) {
        StringBuilder sb = new StringBuilder();
        sb.append(dateTimeFlag);
        sb.append(idValue);
        sb.append(channelCode);
        sb.append(zeroFlag);
        sb.append(uidFlag);
        return sb.toString();
    }
}
