package com.vip.validator;

import com.alibaba.fastjson.JSON;
import com.vip.validator.model.UserModel;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class TestCustomAnnotation {
    public static void main(String[] args) {
        UserModel model = new UserModel();
        model.setBirthDate("20171118");
        model.setTelephone("021-5623555");
        System.out.println("model :" + JSON.toJSONString(model));
    }
}
