package com.ldc.springLearn.interceptor;

import com.ldc.springLearn.annotation.RpcResultParamCheck;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
public class RpcResultParamValidator implements MethodInterceptor{

    private Logger LOGGER = LoggerFactory.getLogger(RpcResultParamValidator.class);

    // 把一个字符串的第一个字母大写
    private static String getMethodName(String fieldName) throws Exception{
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Object returnObj = methodInvocation.proceed();

        Annotation[] annotations = methodInvocation.getMethod().getAnnotations();
        if (annotations == null || annotations.length <= 0) {
            return returnObj;
        }

        for (Annotation annotation : annotations) {
            if (annotation instanceof RpcResultParamCheck) {
                RpcResultParamCheck rpcResultParamCheckAnt = (RpcResultParamCheck) annotation;

                if (rpcResultParamCheckAnt.isNeedValidate()) {

                    if (returnObj == null) {
                        LOGGER.error("==> RpcResultParamValidator, required output object is null!");
                        throw new NullPointerException("required output object is null! ");

                    } else {

                        String needValidateField = rpcResultParamCheckAnt.validateField();
                        Field[] fields = returnObj.getClass().getDeclaredFields();

                        for (Field field : fields) {
                            if (field.getName().equalsIgnoreCase(needValidateField)) {
                                // 获取该字段对应的getter方法
                                Method m = (Method) returnObj.getClass().getMethod("get" + getMethodName(field.getName()));
                                // 调用getter方法获取属性值
                                String fieldValue = (String) m.invoke(returnObj);

                                if (StringUtils.isBlank(fieldValue)) {
                                    LOGGER.error("==> RpcResultParamValidator, class {} required output param: {} is null!", methodInvocation.getMethod().getName(), field.getName());
                                    throw new IllegalArgumentException("class {} " + methodInvocation.getMethod().getName() + " required output param : " + field.getName() + " is null!");
                                }
                            } else
                                continue;
                        }
                    }
                }
            }

        }

        return returnObj;
    }
}