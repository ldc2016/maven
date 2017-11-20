package com.ldc.common.enums;

/**
 * Created by dacheng.liu on 2017/10/13.
 */
public enum ErrorEnum {

    SIGN_ERROR("100016","签名错误"),
    param_object_not_exists("4000001", "参数不足, 缺少必要参数：{0}"),
    required_input_param_not_exists("4000002", "必需参数：{0} 为空"),
    required_output_param_not_exists("4000003", "必输出参数：{0} 为空"),
    param_format_not_exists("4000004", "参数：{0} 格式不正确，正确格式为：{1}");


    private String errorCode;
    private String errorMessage;

    ErrorEnum(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
