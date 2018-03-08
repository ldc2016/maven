package com.ldc.springLearn.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class BusinessAroundLoggerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessAroundLoggerAspect.class);

    // 定义切点
    @Pointcut("execution(public * com.ldc.common.service.UserInfoService.*(..))")
    public void pointCutMethod() {

    }

    // 定义环绕增强
    @Around("pointCutMethod()")
    public Object aroundLogRequest(final ProceedingJoinPoint point) throws Throwable {
        String parameters = JSON.toJSONString(point.getArgs());

        Object[] args=point.getArgs();

        LOGGER.info("[BusinessAroundLoggerAspect.aroundLogRequest] -> enhance target class :" + point.getTarget());
        LOGGER.info("[BusinessAroundLoggerAspect.aroundLogRequest] -> enhance method : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        LOGGER.info("[BusinessAroundLoggerAspect.aroundLogRequest] -> request arguments :"+ JSON.toJSONString(args));

        try {
            Object result = point.proceed();
            LOGGER.info("[BusinessAroundLoggerAspect.aroundLogRequest] -> response :" + JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            LOGGER.error("[BusinessAroundLoggerAspect.aroundLogRequest] -> happen error when call method " + point.getSignature().getName() + ", Parameters : " + parameters, e);
            throw e;
        }
    }

}
