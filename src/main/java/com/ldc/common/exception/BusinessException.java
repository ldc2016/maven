package com.ldc.common.exception;

import com.ldc.common.entity.ErrorEntity;

/**
 * Created by dacheng.liu on 2017/10/13.
 */
public class BusinessException extends RuntimeException{

    private String errorCode;
    private String errorMessage;
    private String errorMethodName;

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }

    public BusinessException(ErrorEntity fcgErrorEntity) {
        super(fcgErrorEntity.getErrorMessage());
        this.errorMessage = fcgErrorEntity.getErrorMessage();
        this.errorCode = fcgErrorEntity.getErrorCode();

        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            this.errorMethodName = "Unknown method name";
            if (ste.getClassName().endsWith("Controller")) {
                this.errorMethodName = ste.getMethodName();
                break;
            }
        }
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

    public String getErrorMethodName() {
        return errorMethodName;
    }
}
