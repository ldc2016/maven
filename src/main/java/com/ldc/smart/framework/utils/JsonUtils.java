package com.ldc.smart.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dacheng.liu on 2017/4/27.
 */
public final class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 对象转化为json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static<T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonUtils.toJson() happen Exception",e);
            throw new RuntimeException(e.getMessage());
        }
        return json;
    }

    /**
     * 将 json字符创转化为对象
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static<T> T jsonToObject(String json, Class<?> type){
        T pojo;
        try {
            pojo = (T) OBJECT_MAPPER.readValue(json,type);
        } catch (Exception e) {
            LOGGER.error("JsonUtils.jsonToObject() happen Exception",e);
            throw new RuntimeException(e.getMessage());
        }
        return pojo;
    }


}
