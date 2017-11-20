package com.ldc.springLearn.contextLoader;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Created by dacheng.liu on 2017/9/5.
 */
public class MyContextLoaderListener extends ContextLoader implements ServletContextListener{

    Logger logger = LoggerFactory.getLogger(MyContextLoaderListener.class);

    private ServletContext context = null;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("MyContextLoaderListener.contextInitialized, ************ start ***********");
        this.context = servletContextEvent.getServletContext();
        logger.info("ServletContext in MyContextLoaderListener is :{}",JSON.toJSONString(context));
        logger.info("MyContextLoaderListener.contextInitialized, ************ end ***********");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("MyContextLoaderListener.contextDestroyed, ************ start ***********");
        this.context = null;
        logger.info("MyContextLoaderListener.contextDestroyed, ************ end ***********");
    }

}
