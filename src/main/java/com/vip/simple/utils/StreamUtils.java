package com.vip.simple.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dacheng.liu on 2017/4/27.
 */
public final class StreamUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

    public static String getStringFromInputStream(InputStream inputStream){
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("StreamUtils.getStringFromInputStream(), happen exception",e);
            throw new RuntimeException(e.getMessage());
        }

        return sb.toString();
    }
}
