package com.vip.springLearn.contextLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by dacheng.liu on 2017/11/16.
 */

@Component
public class SpringIocBridge implements ApplicationContextAware {

    private final static Logger LOG = LoggerFactory.getLogger(SpringIocBridge.class);

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringIocBridge.applicationContext = applicationContext;
    }


    /**
     * 根据Class类型获取bean实例
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return checkApplicationContext().getBean(requiredType);
    }

    /**
     * 根据beanName获取bean实例
     * @param name
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }


    private static ApplicationContext checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("===>SpringIocBridge.checkApplicationContext,applicationContext is not initialized, please check spring configuration!");
        }
        return applicationContext;
    }

    /**
     *
     * Auto wire bean if under spring context. <br/>
     * @return the auto wired bean if under spring context, or return the original bean.
     */
    public static <T> T autowireBean(T bean) {
        if (bean != null && applicationContext != null) {
            AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
            factory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        }
        return bean;
    }
}
