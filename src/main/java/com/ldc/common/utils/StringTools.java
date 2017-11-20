package com.ldc.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class StringTools {

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     */
    public static String makeSortString(Map<String, String> params){
        return makeSortString(params, false);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String makeSortString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String sortedStr = "";
        String charset = params.get("_default_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {

                }
            }
            if (i == keys.size() - 1) {
                sortedStr = sortedStr + key + "=" + value;
            } else {
                sortedStr = sortedStr + key + "=" + value + "&";
            }
        }

        return sortedStr;
    }

    /**
     * 获取左边的字符串
     * @param originalStr 输入字符串
     * @param len 最终长度
     * @return 返回格式化后字符串
     */
    public static String left(String originalStr, int len) {
        return originalStr == null?null:(len < 0?"":(originalStr.length() <= len?originalStr:originalStr.substring(0, len)));
    }

    /**
     * 如果为空，用默认值替换
     * @param originalStr 入参
     * @param defaultStr 默认值
     * @return 返回数据
     */
    public static String defaultIfBlank(String originalStr, String defaultStr) {
        return isBlank(originalStr)?defaultStr:originalStr;
    }

    /**
     * 判断str是否为空白
     * @param originalStr 入参
     * @return 返回是否空白
     */
    public static boolean isBlank(String originalStr) {
        int strLength;
        if(originalStr != null && (strLength = originalStr.length()) != 0) {
            for(int i = 0; i < strLength; ++i) {
                if(!Character.isWhitespace(originalStr.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 左补齐方法
     * @param originalStr 入参
     * @param size 长度
     * @param coverStr 补齐字符
     */
    public static String alignRight(String originalStr, int size, String coverStr) {
        if(originalStr == null) {
            return null;
        } else {
            if(coverStr == null || coverStr.length() == 0) {
                coverStr = " "; // 默认情况下是空格字符
            }

            int coverLen = coverStr.length();
            int originalLen = originalStr.length();
            int covers = size - originalLen;
            if(covers <= 0) {
                return originalStr;
            } else if(covers == coverLen) {
                return coverStr.concat(originalStr);
            } else if(covers < coverLen) {
                return coverStr.substring(0, covers).concat(originalStr);
            } else {
                char[] padding = new char[covers];
                char[] padChars = coverStr.toCharArray();

                for(int i = 0; i < covers; ++i) {
                    padding[i] = padChars[i % coverLen];
                }

                return (new String(padding)).concat(originalStr);
            }
        }
    }


    /**
     * 截取字符串
     */
    public static String substring(String originalStr, int start, int end) {
        if(originalStr == null) {
            return null;
        } else {
            if(end < 0) {
                end += originalStr.length();
            }

            if(start < 0) {
                start += originalStr.length();
            }

            if(end > originalStr.length()) {
                end = originalStr.length();
            }

            if(start > end) {
                return "";
            } else {
                if(start < 0) {
                    start = 0;
                }

                if(end < 0) {
                    end = 0;
                }

                return originalStr.substring(start, end);
            }
        }
    }
}
