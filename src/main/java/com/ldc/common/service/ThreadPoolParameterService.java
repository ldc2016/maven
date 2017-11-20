package com.ldc.common.service;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public interface ThreadPoolParameterService {

    boolean isOpenBySwitchName(String executableSwitch, String start);

    long getDelayTimeValueByName(String delayTimeName, long l);
}
