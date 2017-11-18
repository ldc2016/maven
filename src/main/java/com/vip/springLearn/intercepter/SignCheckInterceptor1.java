package com.vip.springLearn.intercepter;

import com.vip.common.utils.MD5EncryptUtils;
import com.vip.common.utils.SingCheckRequestParamUtils;
import com.vip.common.utils.StringSortTools;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SignCheckInterceptor1 implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SignCheckInterceptor1.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        logger.error("==>SignCheckInterceptor1.invoke ： " + start);

        try{
            /**获取待接口验签的方法的入参*/
            Object[] args = invocation.getArguments();

            // 将请求参数转化为Map
            Map<String, String> paramMap = SingCheckRequestParamUtils.beanObjectToMap(args[0]);

            // 获取参数中的sign
            String signRemote = SingCheckRequestParamUtils.getParamValueByName(args[1],"sign").toString();

            // 对请求参数按key的首字母进行排序
            String content = StringSortTools.makeSortString(paramMap);
            logger.error("==>SignCheckInterceptor1.invoke, 待加密字符串 is {}",content);

            String signLocal = MD5EncryptUtils.makeSign(content, MD5EncryptUtils.MD5_SIGN_KEY, MD5EncryptUtils.DEFAULT_CHARSET);
            logger.error("==>SignCheckInterceptor1.invoke, local sign is : " + signLocal);

            if(!signLocal.equals(signRemote)){
                logger.error("==>SignCheckInterceptor1.invoke, 预期sign: {}, 调用方传入的sign: {}",signLocal,signRemote);
                throw new RuntimeException("签名不一致！");
            }else{
                Object result = invocation.proceed();
                return result;
            }

        }catch (RuntimeException e){
            logger.error("==>SignCheckInterceptor1.invoke, 验签发生系统内部异常，异常信息：{}", e.getMessage(), e);
            throw new RuntimeException("接口验签发生系统内部异常",e);
        }

    }

}