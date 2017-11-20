package com.ldc.common.vph;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class CallBackResultUtils {

    public static CallBackResult<T> getCallBackResultByJsonString(String json,Class z){

        // 参数非空校验
        if(json == null){
            return null;
        }
        CallBackResult<T>  callBackResult = new CallBackResult<>();

        JSONObject jsobject = JSONObject.parseObject(json);
        // get resultCode
        String resultCode = jsobject.getString("resultCode");
        if(StringUtils.isNotBlank(resultCode)){
            callBackResult.setResultCode(resultCode);
        }

        // get resultMessage
        String resultMessage = jsobject.getString("resultMessage");
        if(StringUtils.isNotBlank(resultMessage)){
            callBackResult.setMessage(resultMessage);
        }

        // get successCode
        Boolean successCode = jsobject.getBooleanValue("successCode");
        if(successCode != null){
            callBackResult.setSuccess(successCode);
        }

        // get result
        String results = jsobject.getString("results");
        if(StringUtils.isNotBlank(results)){
            JSONObject callBackResultsJsObject = JSONObject.parseObject(results);
            String defaults = callBackResultsJsObject.getString("_defaultResult");
            if(defaults != null){
                T t =  (T) JSON.parseObject(defaults,z);
                callBackResult.setDefaultResult(t);
            }
            for(String key : callBackResultsJsObject.keySet()){
                if("_defaultResult".equals(key)){
                    continue;
                }
                else{
                    callBackResult.setResult(key, callBackResultsJsObject.get(key));
                }
            }
        }

        return callBackResult;

    }
}
