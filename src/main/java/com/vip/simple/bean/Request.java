package com.vip.simple.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by dacheng.liu on 2017/4/27.
 * 请求对象，封装请求方法，请求路径
 */
public class Request {
    private String requestMethod;
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
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
