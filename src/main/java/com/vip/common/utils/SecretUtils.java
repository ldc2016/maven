package com.vip.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

/**
 * Created by dacheng.liu on 2017/9/28.
 */
public class SecretUtils {

    // 定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String Algorithm = "DESede";
    private static final String PASSWORD_CRYPT_KEY = "2015MoankerVipshop";

    /*
     * 根据字符串生成密钥字节数组
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        final byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
        final byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
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
            final SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
            final Cipher c1 = Cipher.getInstance(Algorithm);
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
            ////e1.printStackTrace();
        }
        return null;
    }

    public static String decryptMode(String str, String key) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        try {
            final SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm);
            final Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
            return new String(c1.doFinal(Base64.decode(str)));
        } catch (final Exception e) {
            ////e.printStackTrace();
        }
        return null;

    }

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encryptMode(byte[] src) {
        try {
            final SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm); // 生成密钥
            final Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
            return c1.doFinal(src);
        } catch (final Exception e) {
            // e
        }
        return null;
    }

    /**
     * 加密方法
     *
     * @param str 源数据的字符串
     * @return
     */
    public static String encryptMode(String str) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        // 加密
        final byte[] secretArr = SecretUtils.encryptMode(str.getBytes());
        return Base64.encode(secretArr);

    }


    public static String encryptMode(String str, String key) {
        if (null == str || "".equals(str.trim())) {
            return null;
        }
        try {
            final SecretKey deskey = new SecretKeySpec(build3DesKey(key), Algorithm); // 生成密钥
            final Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
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

