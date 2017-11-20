package com.ldc.validator.annotation;

import com.ldc.validator.CheckDateFormatValidator;

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
    boolean isRequired() default false;
    String format() default "yyyy-MM-dd";
    String msg() default "生日日期格式非法";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
