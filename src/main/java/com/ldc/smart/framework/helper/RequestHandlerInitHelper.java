package com.ldc.smart.framework.helper;

import com.ldc.smart.framework.bean.Handler;
import com.ldc.smart.framework.bean.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/4/27.
 *
 *
 */
public final class RequestHandlerInitHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandlerInitHelper.class);

    private static Map<Request,Handler> ACTION_MAP = new HashMap<>();

//    static{
//        // 获取所有的controller类
//        Set<Class<?>> controllerClassSet = ClassLoadHelper.getCoontrollerClasseSet();
//        if(CollectionUtils.isNotEmpty(controllerClassSet)){
//           // 循环遍历Controller类，并获取每个controller类中定义的方法
//            for(Class<?> beanClass: controllerClassSet){
//                Method[] methods = beanClass.getMethods();
//
//                // 遍历beanClass类中的method，判断方法上是否有@Action注解，若有则初始化到Action_map中
//                if(ArrayUtils.isNotEmpty(methods)){
//
//                    for(Method method:methods){
//                        if(method.isAnnotationPresent(Action.class)){
//                            Action action = method.getAnnotation(Action.class);
//                            String requestMethodAndPath = action.value();
//                            // 验证url映射规则
//                            if(requestMethodAndPath.matches("\\W+:/\\W*")){
//
//                                String[] array = requestMethodAndPath.split(":");
//                                if(ArrayUtils.isNotEmpty(array) && array.length == 2){
//                                    // 获取请求方法和请求路径
//                                    String requestMethod = array[0];
//                                    String requestPath = array[1];
//                                    Request request = new Request(requestMethod,requestPath);
//
//                                    // 封装请求处理的实例对象
//                                    Handler handler = new Handler(method,beanClass);
//
//                                    // 将请求-处理映射加入到ActionMap中
//                                    ACTION_MAP.put(request,handler);
//
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        if(!ACTION_MAP.containsKey(request)){
            throw new RuntimeException("RequestHandlerInitHelper.getHandler() happen exception!");
        }
        return ACTION_MAP.get(request);
    }
}
