package com.vip.validator.model;

import com.vip.validator.annotation.CheckDateFormat;
import com.vip.validator.annotation.CheckTelephoneFormat;
import lombok.Data;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
@Data
public class UserModel {
    private String name;

    @CheckDateFormat(format = "yyyy-MM-dd",required = true)
    private String birthDate;
    @CheckTelephoneFormat(required = true,message = "座机号码格式有误")
    private String telephone;
}
