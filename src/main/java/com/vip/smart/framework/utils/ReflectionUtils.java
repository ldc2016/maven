package com.vip.smart.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by dacheng.liu on 2017/4/27.
 * 主要用于提供通过class的全路径名称来实例化对象的一些方法
 * 包装了底层的一些反射API
 */
public final class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 根据class来初始化其对应的对象
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance =  cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("ReflectionUtils.newInstance() happened Exception!" ,e);
            throw  new RuntimeException(e.getMessage());
        }
        return instance;
    }


    /**
     * 调用 instance 实例中的method方法
     * @param instance
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object instance, Method method, Object... args){
        Object result ; // 调用方法时返回的结果
        try {
            // 调用method前，强制使得该方法可访问（否则调private方法会报异常）
            method.setAccessible(true);
            result = method.invoke(instance,args);
        } catch (Exception e) {
            LOGGER.error("ReflectionUtils.invokeMethod() happened Exception!" ,e);
            throw new RuntimeException(e.getMessage());
        }

        return result;
    }

    public static void setFileds(Object instance, Field filed, Object value){

        try {
            // 设置成员字段的值前，强制使得该成员字段可访问（否则调private成员变量式会报异常）
            filed.setAccessible(true);
            filed.set(instance,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("ReflectionUtils.setFileds() happened Exception!" ,e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
