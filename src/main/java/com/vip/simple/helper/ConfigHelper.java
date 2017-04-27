package com.vip.simple.helper;

import com.vip.simple.constants.ConfigConstant;
import com.vip.simple.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by dacheng.liu on 2017/4/27.
 */
public final class ConfigHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHelper.class);

    private static final Properties properties = PropertiesUtil.loadProps(ConfigConstant.CONFIG_FILE);


    public static String getJdbcDriver(){
        return properties.getProperty(ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUsername(){
        return properties.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return properties.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackageName(){
        return properties.getProperty(ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath(){
        return properties.getProperty(ConfigConstant.JSP_PATH,"/WEB-INF/jsp");
    }


}
