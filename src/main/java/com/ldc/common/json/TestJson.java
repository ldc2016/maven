package com.ldc.common.json;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ldc.common.entity.UserInfoModel;

/**
 * created by liudacheng on 2018/1/29.
 */
public class TestJson {

    public static void main(String[] args) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserName("张三");

        System.out.println("fastJson : " + JSON.toJSON(userInfoModel));
        System.out.println("gsonJson : " + new Gson().toJson(userInfoModel));


        Integer a = new Integer(1);
        Integer b = null;
        System.out.println("a == b " + (a.equals(b)));
    }
}
