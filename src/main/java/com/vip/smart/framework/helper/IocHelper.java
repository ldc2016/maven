package com.vip.smart.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dacheng.liu on 2017/4/27.
 * 依赖注入功能：只需在静态代码块中实现相关逻辑，就能完成IOC容器的初始化工作，
 *             当IocHelper这个类被加载时，就会加载他的静态代码块
 * 实现方式：1.通过BeanInstanceHelper 实例化了最外层的类（Controller及Service）并封装在BEAN_MAP中
 *         2.遍历BEAN_MAP，取得对应的Bean的class对象和bean实例
 *         3.通过反射类获取class中的所有成员变量，一次遍历这个成员变量集合，并判断当前成员变量上是否有@inject属性，
 *           若有则从BEAN_MAP中取出bean类和bean实例，并使用ReflectionUtils类设置该成员变量的值
 */
public final class IocHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);
//    static{
//        // 获取所有的bean类 与bean实例之间的映射集合（BEAN_MAP）
//        Map<Class<?>,Object> BEAN_MAP = BeanInstanceHelper.getBeanMap();
//        if(BEAN_MAP != null){
//            for(Map.Entry<Class<?>, Object> entry : BEAN_MAP.entrySet()){
//                Class<?> beanClass = entry.getKey();
//                Object   beanInstance = entry.getValue();
//
//                // 获取beanClass中定义的所有成员变量
//                Field[] fields = beanClass.getDeclaredFields();
//
//                if(!ArrayUtils.isEmpty(fields)){
//                    // 遍历成员变量字段，如成员变量字段被@Inject注解修饰，则使用ReflectionUtils设置
//                    for(Field field : fields){
//                        if(field.isAnnotationPresent(Inject.class)){
//                            // 根据filed的class去bean容器中获取对应bean实例
//                            Object filedInstance = BEAN_MAP.get(field.getType());
//                            // 通过反射初始化beanField的值
//                            ReflectionUtils.setFileds(beanInstance,field,filedInstance);
//                        }
//
//                    }
//                }
//
//            }
//
//        }
//
//    }
}
