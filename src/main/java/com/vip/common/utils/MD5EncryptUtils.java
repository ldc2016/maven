package com.vip.common.utils;

import java.util.Map;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class MD5EncryptUtils {
    public static final String MD5_SIGN_KEY = "jAw_(08wBn~SFB1W+82(i@bVJo*=XCVt<7";
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static String makeSign(String content) throws Exception {
        return MD5Utils.makeSign(content, MD5_SIGN_KEY, "UTF-8");
    }

    public static boolean checkSign(String content, String signMsg) throws Exception {
        return MD5Utils.validateSign(content, signMsg,MD5_SIGN_KEY, DEFAULT_CHARSET);
    }

    private static boolean checkSign(String content, String signMsg, String signKey, String charset) throws Exception {
        return MD5Utils.validateSign(content, signMsg, signKey, charset);
    }

    public static String makeSign(Map<String,String> originalParams) throws Exception{
        if ((originalParams != null) && (!originalParams.isEmpty())) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,String> entry : originalParams.entrySet()) {
                sb.append("&").append(entry.getKey()).append("=");
                String value = entry.getValue();
                if (value != null) {
                    sb.append(value);
                }
            }
            String plainText = sb.toString();
            if(plainText.length()>0){
                plainText = plainText.substring(1);
            }
        }
        return makeSign("");
    }

    public static String makeSign(String content, String signKey, String charset) throws Exception {
        return MD5Utils.makeSign(content, signKey, charset);
    }
}
