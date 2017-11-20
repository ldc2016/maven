package com.ldc.springLearn.ioc;

import com.alibaba.fastjson.JSON;
import com.ldc.common.entity.UserInfoModel;
import com.ldc.common.service.UserInfoService;
import com.ldc.common.service.iml.UserInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.StandardEnvironment;

/**
 * Created by dacheng.liu on 2017/9/6.
 */
public class SpringBridageUtils {

    private static Logger logger = LoggerFactory.getLogger(SpringBridageUtils.class);
    private static StandardEnvironment environment = null;
    private static AbstractApplicationContext applicationContext = null;

    static{
        applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext.xml"},false,null);
        environment = (StandardEnvironment) applicationContext.getEnvironment();
        environment.setActiveProfiles("development");
        applicationContext.setEnvironment(environment);
        applicationContext.refresh();
    }

    public static void main(String[] args) {
        System.out.println("***********     SpringBridageUtils 开始加载***************** ");

        UserInfoService service = applicationContext.getBean("userInfoServiceImpl", UserInfoServiceImpl.class);
        UserInfoModel model = service.getUserInfoModelByUserNo("20161228165343000000000002002012");
        logger.info("SpringBridageUtils.main, 通过静态代码块应用启动的IOC容器，并获取spring管理的bean! :{}", JSON.toJSONString(model));


        logger.info("****** systemProperty in environment of this applicationContext is :{}",environment.getSystemEnvironment());
        logger.info("SpringBridageUtilsH啊.main, applicationContext is Active :{}",applicationContext.isActive());

        applicationContext.destroy();

        logger.info("SpringBridageUtils.main, applicationContext is Active :{}",applicationContext.isActive());

    }

}
