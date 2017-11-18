package com.vip.common.vph;

import java.util.Map;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public interface ICallBackResult<T> {

    /**
     * 在CallBackResult表中代表默认的result的key。
     */
    String DEFAULT_KEY = "_default";

    /**
     * 请求是否成功
     */
    boolean isSuccess();

    /**
     * 取得result对象。
     */
    T getDefaultResult();

    void setSuccess(boolean success);

    /**
     * 取得result对象的key
     */
    String getDefaultKey();

    /**
     * 设置默认result对象。
     *
     */
    void setDefaultResult(T result);

    /**
     * 设置result对象
     * @param key   字符串key
     * @param result result对象
     */
    void setDefaultResult(String key, Object result);

    /**
     * 设置result对象
     * @param key   字符串key
     * @param result result对象
     */
    void setResult(String key, Object result);

    /**
     * 取得所有result对象。
     */
    Map getResults();

    int getCode();

    String getMessage();

    void setCodeMessage(int code, String message);
}
