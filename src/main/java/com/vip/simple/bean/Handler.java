package com.vip.simple.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.lang.reflect.Method;

/**
 * Created by dacheng.liu on 2017/4/27.
 * 处理对象 封装处理类，处理方法
 */
public class Handler {
    private Class<?> controllerClass;
    private Method actionMethod;

    public Handler(Method actionMethod, Class<?> controllerClass) {
        this.actionMethod = actionMethod;
        this.controllerClass = controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o);

    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
