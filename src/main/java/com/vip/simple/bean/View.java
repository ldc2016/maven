package com.vip.simple.bean;

import java.util.Map;

/**
 * Created by dacheng.liu on 2017/4/27.
 */
public class View {
    private String path; //视图路径
    private Map<String,Object> model;// 模型数据

    public View(Map<String, Object> model, String path) {
        this.model = model;
        this.path = path;
    }

    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public String getPath() {
        return path;
    }
}
