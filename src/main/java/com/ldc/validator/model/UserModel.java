package com.ldc.validator.model;

import com.ldc.validator.annotation.CheckDateFormat;
import com.ldc.validator.annotation.CheckTelephoneFormat;
import lombok.Data;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
@Data
public class UserModel {
    private String name;

    @CheckDateFormat(isRequired = true, format = "yyyy-MM-dd")
    private String birthDate;
    @CheckTelephoneFormat(isRequired = true,msg = "座机号码格式有误")
    private String telephone;
}
