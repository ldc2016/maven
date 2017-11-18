package com.vip.common.vph;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/11/15.
 */
public class CallBackResult<T> implements ICallBackResult<T> {


    private static final long serialVersionUID = 5693109708815380639L;

    private Map callBackResult = new HashMap(4);

    private String defaultKey;

    /**
     * 返回码和返回消息
     * 消息状态返回码
     * 默认返回false
     *  0 false
     *  0 true
     */
    private int successCode = 0;

    /**
     * 业务返回码
     */
    private String resultCode="200";

    private String resultMessage="ok";

    /**
     * 默认是失败
     */
    public CallBackResult() {
    }

    /**
     * 根据传入是否成功创建
     * @param success
     */
    public CallBackResult(boolean success) {
        super();
        setSuccess(success);
    }

    @Override
    public boolean isSuccess() {
        return successCode > 0;
    }

    @Override
    public void setSuccess(boolean success) {
        this.successCode = success?1:0;
    }

    @Override
    public String getDefaultKey() {
        return ((defaultKey == null) || (defaultKey.length() == 0)) ? DEFAULT_KEY : defaultKey;
    }

    @Override
    public T getDefaultResult() {
        return (T) callBackResult.get(getDefaultKey());
    }

    @Override
    public void setDefaultResult(T result) {
        setDefaultResult(DEFAULT_KEY, result);
    }

    @Override
    public void setDefaultResult(String key, Object result) {
        defaultKey = ((key == null) || (key.length() == 0)) ? DEFAULT_KEY : key;
        callBackResult.put(key, result);
    }

    @Override
    public void setResult(String key, Object result) {
        callBackResult.put(key, result);
    }

    @Override
    public Map getResults() {
        return this.callBackResult;
    }

    @Override
    public int getCode() {
        return this.successCode;
    }

    @Override
    public String getMessage() {
        return this.resultMessage;
    }

    @Override
    public void setCodeMessage(int code, String message) {
        this.successCode=code;
        this.resultMessage=message;

    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setMessage(String message) {
        this.resultMessage = message;
    }

    public String toJsonResult() {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(CallBackResult.class, "success",
                "results", "resultCode", "message");
        return JSON.toJSONString(this, filter);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Result {\n");
        buffer.append("    code    = ").append(resultCode).append(",\n");
        buffer.append("    message    = ").append(resultMessage).append(",\n");
        buffer.append("    defaultKey    = ").append(defaultKey).append(",\n");
        buffer.append("    result     = {");


        if (callBackResult.isEmpty()) {
            buffer.append("}\n");
        } else {
            buffer.append("\n");

            for (Iterator i = callBackResult.entrySet().iterator(); i.hasNext();) {
                Map.Entry entry = (Map.Entry) i.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                buffer.append("        ").append(key).append(" = ");
                if (value != null) {
                    buffer.append("(").append(getClassNameForObject(value)).append(") ");
                }
                buffer.append(value);
                if (i.hasNext()) {
                    buffer.append(",");
                }
                buffer.append("\n");
            }
            buffer.append("    }\n");
        }
        buffer.append("}");
        return buffer.toString();
    }

    private String getClassNameForObject(Object object) {
        if (object == null) {
            return null;
        }
        return getClassName(object.getClass().getName(), true);
    }

    private String getClassName(String className, boolean processInnerClass) {
        if ((className == null) || (className.length() == 0)) {
            return className;
        }

        if (processInnerClass) {
            className = className.replace("$", ".");
        }

        int length = className.length();
        int dimension = 0;

        // 取得数组的维数，如果不是数组，维数为0
        for (int i = 0; i < length; i++, dimension++) {
            if (className.charAt(i) != '[') {
                break;
            }
        }

        // 如果不是数组，则直接返回
        if (dimension == 0) {
            return className;
        }

        // 确保类名合法
        if (length <= dimension) {
            return className; // 非法类名
        }

        // 处理数组
        StringBuffer componentTypeName = new StringBuffer();

        switch (className.charAt(dimension)) {
            case 'Z':
                componentTypeName.append("boolean");
                break;

            case 'B':
                componentTypeName.append("byte");
                break;

            case 'C':
                componentTypeName.append("char");
                break;

            case 'D':
                componentTypeName.append("double");
                break;

            case 'F':
                componentTypeName.append("float");
                break;

            case 'I':
                componentTypeName.append("int");
                break;

            case 'J':
                componentTypeName.append("long");
                break;

            case 'S':
                componentTypeName.append("short");
                break;

            case 'L':

                if ((className.charAt(length - 1) != ';') || (length <= (dimension + 2))) {
                    return className; // 非法类名
                }

                componentTypeName.append(className.substring(dimension + 1, length - 1));
                break;

            default:
                return className; // 非法类名
        }

        for (int i = 0; i < dimension; i++) {
            componentTypeName.append("[]");
        }

        return componentTypeName.toString();
    }
}
