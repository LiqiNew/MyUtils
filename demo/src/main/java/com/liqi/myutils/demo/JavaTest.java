package com.liqi.myutils.demo;

import com.liqi.utils.encoding.JToAAesEncryptor;

/**
 * 模拟java端AES加密解密测试
 * Created by LiQi on 2018/7/2.
 */

public class JavaTest {
    public static final String KEY = "QQ:543945827----";

    public static void main(String[] args) {
        try {
            String encryptContent = JToAAesEncryptor.encrypt(KEY, "加密内容");
            System.out.print("加密出内容：" + encryptContent);


            String decryptContent = JToAAesEncryptor.decrypt(KEY, encryptContent);
            System.out.print("\n解密出内容：" + decryptContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
