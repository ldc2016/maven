package com.vip.simple.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by dacheng.liu on 2017/4/27.
 */
public final class URLCodeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLCodeUtils.class);

    /**
     * 将url编码
     * @param source
     * @return
     */
    public static String encodeURL(String source){
        String target ;
        try {
            target = URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URLCodeUtils.encodecURL() ,happen Exception",e);
            throw new RuntimeException(e.getMessage());
        }
        return target;
    }


    /**
     * 将url解码
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target ;
        try {
            target = URLDecoder.decode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URLCodeUtils.decodeURL() ,happen Exception",e);
            throw new RuntimeException(e.getMessage());
        }
        return target;
    }
}
