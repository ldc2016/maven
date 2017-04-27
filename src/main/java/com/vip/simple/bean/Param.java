package com.vip.simple.bean;

import com.vip.simple.utils.CastUtil;

import java.util.Map;

/**
 * Created by dacheng.liu on 2017/4/27.
 * 从HttpServletRequest对象中获取所有请求参数，并将其初始化到param的对象中
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String,Object> getParamMap(){
        return this.paramMap;
    }
}
