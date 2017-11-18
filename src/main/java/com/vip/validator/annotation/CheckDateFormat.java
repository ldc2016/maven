package com.vip.validator.annotation;

import com.vip.validator.CheckDateFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
@Constraint(validatedBy = CheckDateFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDateFormat {
    String format() default "yyyy-MM-dd";
    boolean required() default false;
    String message() default "生日日期格式非法";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
