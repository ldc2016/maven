package com.vip.validator;

import com.vip.validator.annotation.CheckTelephoneFormat;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class CheckTelephoneFormatValidator implements ConstraintValidator<CheckTelephoneFormat,String> {
    /**
     * 在验证之前调用该方法
     * @param checkTelephoneFormat
     */
    @Override
    public void initialize(CheckTelephoneFormat checkTelephoneFormat) {

    }

    /**
     * 验证座机号格式
     * 验证方法  返回false说明没有通过校验  value的类型T可以有多种类型时
     * @param fieldValue
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String[] telephones = fieldValue.split("-");
            String regionCode=telephones[0];
            String phoneNum=telephones[1];
            if(StringUtils.isBlank(regionCode)){
                return false;
            }

            if(StringUtils.isBlank(phoneNum)){
                return false;
            }

            //校验座机号码中是否存在非数字字符
            if(!checkDigitNumber(regionCode)){
                return false;
            }

            if(!checkDigitNumber(phoneNum)){
                return false;
            }

            if(telephones.length==3){
                if(!checkDigitNumber(telephones[2])){
                    return false;
                }

            }

            if(regionCode.length()!=3 || regionCode.length()!=4){
                return false;
            }

            if(phoneNum.length()>8){
                return false;
            }

            if(phoneNum.length()==7){
                if(checkSameCharacter(phoneNum)){
                    return false;
                }
            }

            if(phoneNum.length()==8){
                if(checkSameCharacter(phoneNum)){
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean checkDigitNumber(String s){
        return Pattern.compile("^[0-9]*$").matcher(s).matches();
    }

    public boolean checkSameCharacter(String value){
        char firstCharacter =value.charAt(0);
        for(int i=1;i<=value.length()-1;i++){
            if(firstCharacter!=value.charAt(i)){
                return false;
            }
        }
        return true;
    }
}
