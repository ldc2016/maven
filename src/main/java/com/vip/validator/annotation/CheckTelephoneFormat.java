package com.vip.validator.annotation;

import com.vip.validator.CheckTelephoneFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
@Constraint(validatedBy = CheckTelephoneFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckTelephoneFormat {
    boolean isRequired() default false;
    String msg() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
