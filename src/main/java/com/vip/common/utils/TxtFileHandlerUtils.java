package com.vip.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by dacheng.liu on 2017/9/28.
 */
public class TxtFileHandlerUtils {

    private static Logger logger = LoggerFactory.getLogger(TxtFileHandlerUtils.class);

    private static Reader getReader(String inPutFilePath){
        if(StringUtils.isBlank(inPutFilePath)){
            logger.error("TxtFileHandlerUtils.getReader, filePath is null!");
            return null;
        }

        BufferedReader bufferedReader = null;
        try {
            File file = new File(inPutFilePath);
            InputStream inFile = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(inFile));

        } catch (FileNotFoundException e) {
            logger.error("TxtFileHandlerUtils.getReader, FileNotFoundException: {} " + e.getMessage(),e);
            e.printStackTrace();
        }

        return bufferedReader;
    }

    private static Writer getWriter(String outPutFilePath){
        if(StringUtils.isBlank(outPutFilePath)){
            logger.error("TxtFileHandlerUtils.getWriter, outPutFilePath is null!");
            return null;
        }

        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(outPutFilePath);
            OutputStream outputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        } catch (FileNotFoundException e) {
            logger.error("TxtFileHandlerUtils.getWriter, FileNotFoundException: {} " + e.getMessage(),e);
            e.printStackTrace();
        }

        return bufferedWriter;
    }

    private static void closeIoChannel(Reader reader, Writer writer){
        if(reader != null){
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(writer != null){
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {

        String inPutFilePath = "D:\\理财二类户代扣卡号不一致用户信息.txt";
        String outPutFilePath = "D:\\理财二类户代扣卡号不一致用户信息_update.txt";
        String lineData = null;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        BufferedReader bufferedReader = (BufferedReader) getReader(inPutFilePath);
        BufferedWriter bufferedWriter = (BufferedWriter) getWriter(outPutFilePath);

        while (true){
            if((lineData = bufferedReader.readLine()) != null){
                if(count<2){
                    sb.append(lineData).append(",");
                }else{
                    sb.append(lineData);
                }

                count ++;
                if(count == 3){
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    sb.delete(0,sb.length());
                    count = 0;
                }

            }else{
                break;
            }
        }

        closeIoChannel(bufferedReader,bufferedWriter);

        logger.error("******* TxtFileHandlerUtils ********* 处理完毕！");

    }

}



