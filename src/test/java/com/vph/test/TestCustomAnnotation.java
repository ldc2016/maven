package com.vph.test;

import com.alibaba.fastjson.JSON;
import com.ldc.validator.model.UserModel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class TestCustomAnnotation {
    private static Validator validator;

    /**
     * 获取一个验证器
     */
    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void test01(){
        UserModel model = new UserModel();
//        model.setBirthDate("20171118");
//        model.setTelephone("021-5623555");
        System.out.println("model :" + JSON.toJSONString(model));

//        Set<ConstraintViolation<String>> validate = validator.validate(model.getBirthDate());
//        System.out.println(validate.size());
    }
}
