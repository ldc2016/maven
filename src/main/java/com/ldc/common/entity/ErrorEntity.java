package com.ldc.common.entity;


import com.ldc.common.enums.ErrorEnum;

import java.text.MessageFormat;

/**
 * Created by dacheng.liu on 2017/10/13.
 */
public class ErrorEntity {
    private String errorCode;
    private String errorMessage;

    private ErrorEntity() {}

    /**
     * @param errorEnum 业务模块的错误枚举类，包含errorCode、errorMessage和未格式化的errorDetail信息
     * @param args      用来替换待格式化的errorDetail信息中的可替换数据
     */
    public static ErrorEntity constructEntity(ErrorEnum errorEnum, Object... args) {
        ErrorEntity error = new ErrorEntity();
        error.setErrorCode(errorEnum.getErrorCode());
        error.setErrorMessage(MessageFormat.format(errorEnum.getErrorMessage(), args));
        return error;
    }

    public ErrorEntity(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
