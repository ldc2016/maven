package com.ldc.validator;

import com.ldc.validator.annotation.CheckDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class CheckDateFormatValidator implements ConstraintValidator<CheckDateFormat,String> {

    private String datePattern;
    private boolean isRequired;

    public void initialize(CheckDateFormat checkDateFormat){
        this.datePattern = checkDateFormat.format();
        this.isRequired = checkDateFormat.isRequired();
    }


    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if(fieldValue == null){
            return true;
        }
        if(isRequired && fieldValue.equals("")){
            return false;
        }

        if(!isRequired && fieldValue.equals("")){
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
