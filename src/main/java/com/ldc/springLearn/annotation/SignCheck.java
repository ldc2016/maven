package com.ldc.springLearn.annotation;

import java.lang.annotation.*;

/**
 * 接口加密验签注解
 * 被修饰的方法需要进行接口加密验签
 */
@Documented()
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SignCheck {
    String value() default "";
}

