package com.vip.common.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public final class SingCheckRequestParamUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingCheckRequestParamUtils.class);

    /**
     * 根据参数名称获取请求对象中对应的值
     * @param requestObject
     * @param paramName
     * @return
     */
    public static Object getParamValueByName(Object requestObject,String paramName){
        Assert.notNull(requestObject);
        Assert.notNull(paramName);
        LOGGER.info("==>SingCheckRequestParamUtils.getParamValueByName: the name of requestObject is : {}", requestObject.getClass().getName());

        Field[] paramFields = requestObject.getClass().getDeclaredFields();
        if(paramFields == null || paramFields.length == 0){
            LOGGER.error("==>SingCheckRequestParamUtils.getParamValueByName: the paramFields of " + requestObject.getClass().getName() + "is null!");
            throw new RuntimeException("field "+ paramName +" in " + "requestObject not exists! ");
        }

        Object paramValue = null;
        for (Field paramField : paramFields) {
            try{
                paramField.setAccessible(true);
                String fieldName = paramField.getName();
                if(fieldName.equals(paramName)){
                    paramValue = paramField.get(requestObject);
                }

            }catch (IllegalAccessException e){
                LOGGER.error("==>SingCheckRequestParamUtils.getParamValueByName: the error is : {}", e.getMessage(),e);
                throw new RuntimeException(" system exception! ",e);
            }
        }

        LOGGER.info("==>SingCheckRequestParamUtils.getParamValueByName: paramName : {} --> paramValue is:{}",paramName, JSON.toJSON(paramValue));
        return paramValue;
    }

    public static Map<String, String> beanObjectToMap(Object object){
        Map<String, String> map = new HashMap<>();

        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        try{
            for (Field field : fields) {
                field.setAccessible(true);

                // 去除序列化id
                String fieldName = field.getName();
                if(fieldName.equals("serialVersionUID")){
                    continue;
                }
                // 去掉为null的value
                Object fieldValue = field.get(object);
                if(fieldValue != null){
                    map.put(fieldName,fieldValue.toString());
                }
            }
        }catch (IllegalAccessException e){
            LOGGER.error("==>SingCheckRequestParamUtils.beanObjectToMap: the error is : {}", e.getMessage(),e);
            throw new RuntimeException(" system exception! ",e);
        }

        LOGGER.info("==>SingCheckRequestParamUtils.beanObjectToMap: param is:{}", JSON.toJSON(map));
        return map;
    }
}
