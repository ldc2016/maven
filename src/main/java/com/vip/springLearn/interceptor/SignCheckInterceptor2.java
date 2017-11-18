package com.vip.springLearn.interceptor;

import com.alibaba.fastjson.JSON;
import com.vip.common.utils.MD5EncryptUtils;
import com.vip.common.utils.StringSortTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class SignCheckInterceptor2 implements HandlerInterceptor {

    private Logger LOG = LoggerFactory.getLogger(SignCheckInterceptor2.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        {
            LOG.info("******* 开始对调用方进行sign验签 ********");
            // 过滤掉指定url的sign验签
            if(httpServletRequest.getRequestURI().contains("/test/testWebAppStart")){
                return true;
            }

            String remoteSign = httpServletRequest.getParameter("sign");

            //获取请求入参，按指定规则生成sign
            Map httpServletRequestParams = httpServletRequest.getParameterMap();
            Map<String, String> requestParams = new HashMap<>();
            for (Iterator it = httpServletRequestParams.keySet().iterator(); it.hasNext();) {
                String name = (String) it.next();
                if("sign".equals(name)){
                    continue;
                }
                String[] values = (String[]) httpServletRequestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                requestParams.put(name, valueStr);
            }
            String paramText = StringSortTools.makeSortString(requestParams);
            LOG.info("******* 加密前的明文参数：{} *******", paramText);

            String localSign = MD5EncryptUtils.makeSign(paramText,MD5EncryptUtils.MD5_SIGN_KEY,MD5EncryptUtils.DEFAULT_CHARSET);
            LOG.info("******* 根据明文参数生成的本地sign：{} *******", localSign);

            if(!localSign.equals(remoteSign)){
                LOG.error("==>SignCheckInterceptor2.preHandle, 预期sign: {}, 调用方传入的sign: {}",localSign,remoteSign);
                writerToJson("预期sign: "+ localSign +", 调用方传入的sign: " + remoteSign ,httpServletResponse);
                throw new RuntimeException("签名不一致！");
            }

            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void writerToJson(String resp, HttpServletResponse response){
        responseString(JSON.toJSONString(resp), response);
    }

    public void responseString(String data, HttpServletResponse response) {
        response.setContentType("text/plain; charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(data);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }
}
