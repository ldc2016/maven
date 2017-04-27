package com.vip.simple.utils;

/**
 * Created by dacheng.liu on 2017/4/17.
 */

import org.apache.commons.lang3.StringUtils;

/**
 * 转型工具类
 */
public final class CastUtil {


    public static String castString(Object obj, String defaultValue){
        return obj != null? String.valueOf(obj):defaultValue;
    }

    public static String castString(Object obj){
        return obj != null? String.valueOf(obj):"";
    }

    public static double castDouble(Object obj, double defaultValue){
        double value = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try{
                    value = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static double castDouble(Object obj){
        return castDouble(obj);
    }

    public static long castLong(Object obj, long defaultValue){
        long value = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try{
                    value = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static long castLong(Object obj){
        return castLong(obj);
    }

    public static int castInt(Object obj, int defaultValue){
        int value = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try{
                    value = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static int castInt(Object obj){
        return castInt(obj,0);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue){
        boolean value = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try{
                    value = Boolean.parseBoolean(strValue);
                }catch (NumberFormatException e){
                    value = defaultValue;
                }
            }
        }

        return value;
    }

    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }
}
