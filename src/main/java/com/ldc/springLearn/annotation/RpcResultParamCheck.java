package com.ldc.springLearn.annotation;

import java.lang.annotation.*;

@Documented()
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcResultParamCheck {
    String value() default "";
    boolean isNeedValidate() default false;
    String validateField() default "";
}
