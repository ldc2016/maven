package com.vip.common.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class MD5Utils {

    public static String makeSign(String text, String key, String charset) throws Exception {
        text = text + key;
        return DigestUtils.md5DigestAsHex((getContentBytes(text, charset)));
    }

    public static boolean validateSign(String text, String sign, String key, String charset) throws Exception {
        text = text + key;
        String localSign = DigestUtils.md5DigestAsHex((getContentBytes(text, charset)));
        if (localSign.equals(sign)) {
            return true;
        }
        return false;
    }

    private static byte[] getContentBytes(String content, String charset) {
        if ((charset == null) || ("".equals(charset)))
            return content.getBytes();
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,目前指定的编码集是:" + charset,e);
        }
    }

}
