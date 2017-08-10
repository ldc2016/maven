//package com.vip.smart.framework.core;
//
//import com.vip.smart.framework.bean.Data;
//import com.vip.smart.framework.bean.Handler;
//import com.vip.smart.framework.bean.Param;
//import com.vip.smart.framework.bean.View;
//import com.vip.smart.framework.helper.BeanInstanceHelper;
//import com.vip.smart.framework.helper.ConfigHelper;
//import com.vip.smart.framework.helper.FrameworkInitHelper;
//import com.vip.smart.framework.helper.RequestHandlerInitHelper;
//import com.vip.smart.framework.utils.JsonUtils;
//import com.vip.smart.framework.utils.ReflectionUtils;
//import com.vip.smart.framework.utils.StreamUtils;
//import com.vip.smart.framework.utils.URLCodeUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by dacheng.liu on 2017/4/27.
// */
//
//@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
//public class DispatcherServlet extends HttpServlet {
//
//    @Override
//    public void init(ServletConfig servletConfig) throws ServletException {
//
//        // 初始化相关Helper类
//        FrameworkInitHelper.initFramework();
//
//        // 获取ServletContext对象
//        ServletContext servletContext = servletConfig.getServletContext();
//
//        // 注册处理jsp的servlet
//        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//
//        // 注册处理静态资源的默认Servlet
//        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
//    }
//
//    // servlet的核心方法
//    @Override
//    public void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
//
//        // 获取请求方法与请求路径
//        String requestMethod = servletRequest.getMethod();
//        String requestPath = servletRequest.getPathInfo();
//
//        // 构造请求对象，从action_map中获取Handler对象（包含了该请求的controller类和method）
//        Handler handler = RequestHandlerInitHelper.getHandler(requestMethod,requestPath);
//        if(handler != null){
//            // 获取处理请求的controller和method
//            Class<?> controllerClass = handler.getControllerClass();
//            Method actionMethod = handler.getActionMethod();
//
//            // 从Bean_map中获取 bean实例
//            Object controllerBean = BeanInstanceHelper.getBean(controllerClass);
//
//            // 获取请求参数对象
//            Map<String,Object> paramMap = new HashMap<>();
//
//            // (针对get请求)
//            Enumeration<String> paramNames = servletRequest.getParameterNames();
//            if(paramNames!= null){
//                while(paramNames.hasMoreElements()){
//                    String paramName = paramNames.nextElement();
//                    String paramValue = servletRequest.getParameter(paramName);
//                    paramMap.put(paramName,paramValue);
//                }
//            }
//
//            // 获取请求体中的内容（针对post请求）
//            String body = URLCodeUtils.decodeURL(StreamUtils.getStringFromInputStream(servletRequest.getInputStream()));
//            if(StringUtils.isNotEmpty(body)){
//                String[] params = StringUtils.split(body,"&");
//                if(ArrayUtils.isNotEmpty(params)){
//                    for(String param : params){
//                        String[] array = StringUtils.split(param,"=");
//                        if(ArrayUtils.isNotEmpty(array) && array.length == 2){
//                            String paramName = array[0];
//                            String paramValue = array[1];
//                            paramMap.put(paramName,paramValue);
//                        }
//                    }
//                }
//            }
//
//            Param param = new Param(paramMap); // 实例化请求参数
//
//            // 调用Action方法
//            Object result = ReflectionUtils.invokeMethod(controllerBean,actionMethod,param);
//
//            // 处理action方法返回值
//            if(result instanceof View){
//                // 返回jsp页面
//                View view = (View) result;
//                String path = view.getPath();
//                if(StringUtils.isNotEmpty(path)){
//                    if(path.startsWith("/")){
//                        servletResponse.sendRedirect(servletRequest.getContextPath() + path); // 重定向
//                    }else{
//                        Map<String,Object> model = view.getModel();
//                        if(model != null){
//                            for(Map.Entry<String,Object> entry : model.entrySet()){
//                                servletRequest.setAttribute(entry.getKey(),entry.getValue());
//                            }
//                            // 转发
//                            servletRequest.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(servletRequest,servletResponse);
//                        }
//
//                    }
//                }
//            }else if(result instanceof Data){
//                Data data = (Data) result;
//                Object model = data.getModel();
//                if(model != null){
//                    servletResponse.setContentType("application/json");
//                    servletRequest.setCharacterEncoding("UTF-8");
//                    PrintWriter writer = servletResponse.getWriter();
//                    String json = JsonUtils.toJson(model);
//                    writer.write(json);
//                    writer.flush();
//                    writer.close();
//                }
//            }
//
//        }
//    }
//
//    @Override
//    public ServletConfig getServletConfig() {
//        return null;
//    }
//
//    @Override
//    public String getServletInfo() {
//        return null;
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
