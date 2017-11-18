package com.vip.validator;

import com.vip.validator.annotation.CheckDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class CheckDateFormatValidator implements ConstraintValidator<CheckDateFormat,String> {

    private String datePattern;
    private boolean required;

    public void initialize(CheckDateFormat checkDateFormat){
        this.datePattern = checkDateFormat.format();
        this.required = checkDateFormat.required();
    }


    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if(fieldValue == null){
            return true;
        }
        if(required && fieldValue.equals("")){
            return false;
        }

        if(!required && fieldValue.equals("")){
            return true;
        }

        if(fieldValue.length() != datePattern.length()){
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        try{
            format.setLenient(false);
            format.parse(fieldValue);
            return true;
        }catch(Exception e){
            return false;
        }

    }
}
