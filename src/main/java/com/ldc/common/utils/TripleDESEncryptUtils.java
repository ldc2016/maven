package com.ldc.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

/**
 * Created by dacheng.liu on 2017/9/28.
 */
public class TripleDESEncryptUtils {

    // 定义加密算法，有DES、3DES(即DESede)、Blowfish
    private static final String encrypt_algorithm = "DESede";
    private static final String encrypt_key = "2017MockDevelopment";

    /*
     * 根据字符串生成密钥字节数组
     */
    public static byte[] creake3DesKey(String keyStr) throws UnsupportedEncodingException {
        final byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
        final byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

        if (key.length > temp.length) {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] src) {
        try {
            final SecretKey deskey = new SecretKeySpec(creake3DesKey(encrypt_key), encrypt_algorithm);
            final Cipher c1 = Cipher.getInstance(encrypt_algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
            return c1.doFinal(src);
        } catch (final Exception e) {
            ////e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密函数
     *
     * @param str 密文的字节数组
     * @return
     */
    public static String decryptMode(String str) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        try {
            return new String(decryptMode(Base64.decode(str)));
        } catch (final Exception e1) {

        }
        return null;
    }

    public static String decryptMode(String str, String key) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        try {
            final SecretKey deskey = new SecretKeySpec(creake3DesKey(key), encrypt_algorithm);
            final Cipher c1 = Cipher.getInstance(encrypt_algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
            return new String(c1.doFinal(Base64.decode(str)));
        } catch (final Exception e) {
            ////e.printStackTrace();
        }
        return null;

    }

    /**
     * 加密方法
     */
    public static byte[] encryptMode(byte[] src) {
        try {
            final SecretKey deskey = new SecretKeySpec(creake3DesKey(encrypt_key), encrypt_algorithm); // 生成密钥
            final Cipher c1 = Cipher.getInstance(encrypt_algorithm); // 实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, deskey);            // 初始化为加密模式
            return c1.doFinal(src);
        } catch (final Exception e) {
            // e
        }
        return null;
    }

    /**
     * 加密
     */
    public static String encryptMode(String str) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }

        final byte[] secretArr = TripleDESEncryptUtils.encryptMode(str.getBytes());
        return Base64.encode(secretArr);

    }


    public static String encryptMode(String str, String key) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        try {
            final SecretKey deskey = new SecretKeySpec(creake3DesKey(key), encrypt_algorithm); // 生成密钥
            final Cipher c1 = Cipher.getInstance(encrypt_algorithm); // 实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
            final byte[] secretArr = c1.doFinal(str.getBytes());
            return Base64.encode(secretArr);
        } catch (final Exception e) {
            // e
        }
        return null;
    }

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\理财二类户代扣卡号不一致用户信息_update.txt"))));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\理财二类户代扣卡号不一致用户信息_Decode.txt"))));
			String lineData = null;
			StringBuilder sb = new StringBuilder();
			while ((lineData = reader.readLine()) != null) {
				String[] metaData = lineData.split(",");
				metaData[2] = decryptMode(metaData[2]);
				sb.append(metaData[0]).append(", ").append(metaData[1]).append(", ").append(metaData[2]);
				writer.write(sb.toString());
				writer.flush();
				writer.newLine();

				sb.delete(0,sb.length());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

