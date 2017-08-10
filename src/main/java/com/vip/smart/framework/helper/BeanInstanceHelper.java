//package com.vip.smart.framework.helper;
//
//import com.vip.smart.framework.utils.ReflectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by dacheng.liu on 2017/4/27.
// * 功能：获取所有被该框架管理的bean
// * 实现方式：1.通过ClassLoadHelper获取指定包下面的所有class（此时主要指Controller和Service）
// *         2.遍历1中的classSet，调用ReflectionUtils实例化对应class的实例
// *         3.将2中的实例put到全局静态Map的bean容器中，从而实现bean的初始化
// */
//public final class BeanInstanceHelper {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(BeanInstanceHelper.class);
//
//    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();
//
//    /**
//     * jvm加载class时初始化bean容器
//     */
//    static {
//        Set<Class<?>> classSet = ClassLoadHelper.getBeanClasseSet();
//        for(Class<?> cls : classSet){
//            Object instance = ReflectionUtils.newInstance(cls);
//            BEAN_MAP.put(cls,instance);
//        }
//    }
//
//    /**
//     * 获取bean容器
//     * @return
//     */
//    public static Map<Class<?>, Object> getBeanMap(){
//        return  BEAN_MAP;
//    }
//
//    /**
//     * 根据cls获取bean实例
//     * @param cls
//     * @param <T>
//     * @return
//     */
//    public static<T> T getBean(Class<T> cls){
//        if(!BEAN_MAP.containsKey(cls)){
//            throw new RuntimeException("can not get bean by className : " + cls);
//        }
//        return (T)BEAN_MAP.get(cls);
//    }
//}
