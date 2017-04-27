package com.vip.simple.helper;

import com.vip.simple.utils.ClassLoadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dacheng.liu on 2017/4/27.
 */
public final class FrameworkInitHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrameworkInitHelper.class);

    /**
     * 通过init方法可以看出框架启动的流程
     */
    public static void initFramework(){
        Class<?>[] classList = {
                ClassLoadHelper.class,               //  1.加载class类文件
                BeanInstanceHelper.class,            //  2.实例化bean
                IocHelper.class,                     //  3.给Bean注入依赖
                RequestHandlerInitHelper.class       //  4.请求-处理映射初始化
        };

        for(Class<?> cls : classList){
            ClassLoadUtils.loadClass(cls.getName(),false);
        }
    }

}
