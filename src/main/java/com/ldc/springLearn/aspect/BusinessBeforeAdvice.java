package com.ldc.springLearn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class BusinessBeforeAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessBeforeAdvice.class);

    // 定义切点
    @Pointcut("execution(public * com.ldc.common.service.*.*(..))")
    public void pointCutMethod() {

    }

    // 定义环绕增强
    @Before("pointCutMethod()")
    public Object beforeLogRequest(final ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();

        if (args == null || args.length <= 0) {
            LOGGER.error("[BusinessAroundLoggerAspect.beforeLogRequest], method: {} input param is null!", point.getSignature().getName());
            throw new IllegalArgumentException("method：" + point.getSignature().getName() + "input param is null！");
        }

        for (Object arg : args) {
            Class cls = arg.getClass();
            Field[] fields = cls.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(arg);
                if (fieldValue == null) {
                    LOGGER.error("[BusinessAroundLoggerAspect.beforeLogRequest], method {} input param : {} is null!", point.getSignature().getName(), field.getName());
                    throw new IllegalArgumentException("method " + point.getSignature().getName() + " param : " + field.getName() + "is null!");
                } else if ("java.lang.String".equals(fieldValue.getClass().getTypeName()) && "".equals(fieldValue)) {
                    LOGGER.error("[BusinessAroundLoggerAspect.beforeLogRequest], method {} input param : {} is null!", point.getSignature().getName(), field.getName());
                    throw new IllegalArgumentException("method " + point.getSignature().getName() + " param : " + field.getName() + "is null!");
                }
            }
        }
        return point.proceed();
    }

}
