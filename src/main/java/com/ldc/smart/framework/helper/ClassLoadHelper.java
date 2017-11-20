//package com.vip.smart.framework.helper;
//
//
//import com.vip.smart.framework.annotation.Controller;
//import com.vip.smart.framework.annotation.Service;
//import com.vip.smart.framework.utils.ClassLoadUtils;
//
//import java.util.HashSet;
//import java.util.Set;
//
//       /**Created by dacheng.liu on 2017/4/27.
//        *类操作助手:主要用于加载class文件
//        */
//public final class ClassLoadHelper {
//
//    // 定义集合，用于存放所加载的的类
////    private static final Set<Class<?>> CLASSE_SET;
//
//    // 静态代码块，jvm在 应用程序启动时就加载
////    static {
////        String basePackage = ConfigHelper.getAppBasePackageName();
////        CLASSE_SET = ClassLoadUtils.getClassSet(basePackage);
////    }
//
//    /**
//     * 获取应用包名下所有的class
//     * @return
//     */
////    public static Set<Class<?>> getClasseSet(){
////        return CLASSE_SET;
////    }
//
//    /**
//     * 获取应用包名下所有的Service对应的class
//     * @return
//     */
//    public static Set<Class<?>> getServiceClasseSet(){
//        Set<Class<?>> serviceClassSet = new HashSet<Class<?>>();
//        for (Class<?> cls : CLASSE_SET){
//            if(cls.isAnnotationPresent(Service.class)){
//                serviceClassSet.add(cls);
//            }
//        }
//        return serviceClassSet;
//    }
//
//    /**
//     * 获取应用包名下所有的Controller对应的class
//     * @return
//     */
//    public static Set<Class<?>> getCoontrollerClasseSet(){
//        Set<Class<?>> controllerClassSet = new HashSet<Class<?>>();
//        for (Class<?> cls : CLASSE_SET){
//            if(cls.isAnnotationPresent(Controller.class)){
//                controllerClassSet.add(cls);
//            }
//        }
//        return controllerClassSet;
//    }
//
//    /**
//     * 获取应用包名下所有的Bean(当前仅包含Controller类和Service类)
//     * @return
//     */
//    public static Set<Class<?>> getBeanClasseSet(){
//        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
//        beanClassSet.addAll(getCoontrollerClasseSet());
//        beanClassSet.addAll(getServiceClasseSet());
//        return beanClassSet;
//    }
//
//
//}
