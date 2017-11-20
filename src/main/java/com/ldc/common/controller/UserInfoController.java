package com.ldc.common.controller;

import com.alibaba.fastjson.JSON;
import com.ldc.common.entity.UserInfoModel;
import com.ldc.common.service.UserInfoService;
import com.ldc.springLearn.annotation.SignCheck;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dacheng.liu on 2017/8/9.
 */

@Controller
@RequestMapping("/user/test")
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 测试
     * @return
     */
    @RequestMapping(value = "/getUserInfoFormDb")
    @ResponseBody
    @SignCheck
    public String getUserInfoFormDb(String userNo ){
        if(StringUtils.isBlank(userNo)){
            logger.error("userNo is null!");
            return "please input userNo!";
        }
        UserInfoModel userInfoModel = userInfoService.getUserInfoModelByUserNo(userNo);
        logger.error("userInfoModel :{}" ,JSON.toJSONString(userInfoModel));
        return JSON.toJSONString(userInfoModel);
    }


}
