package com.liqi.utils.encoding;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Java端-android系统平台互通加密AES算术加密
 */
public class JToAAesEncryptor {
    private final static String HEX = "0123456789ABCDEF";
    private final static String CODING = "UTF-8";
    private static final String VIPARA = "1269571569321021";

    /**
     * 加密
     *
     * @param seed      加密钥匙（length==16）
     * @param cleartext 要加密内容
     * @param coding    编码
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt(String seed, String cleartext, String coding)
            throws Exception {
        byte[] result = encrypt(seed.getBytes(), cleartext.getBytes(TextUtils.isEmpty(coding) ? CODING : coding));
        return toHex(result);
    }

    /**
     * 加密
     *
     * @param seed      加密钥匙（length==16）
     * @param cleartext 要加密内容
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt(String seed, String cleartext)
            throws Exception {
        byte[] result = encrypt(seed.getBytes(), cleartext.getBytes(CODING));
        return toHex(result);
    }

    /**
     * 解密
     *
     * @param seed      解密钥匙（length==16）
     * @param encrypted 加密内容
     * @param coding    编码
     * @return
     * @throws Exception
     */
    public static String decrypt(String seed, String encrypted, String coding)
            throws Exception {
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(seed.getBytes(), enc);
        return new String(result, TextUtils.isEmpty(coding) ? CODING : coding);
    }

    /**
     * 解密
     *
     * @param seed      解密钥匙（length>=16）
     * @param encrypted 加密内容
     * @return
     * @throws Exception
     */
    public static String decrypt(String seed, String encrypted)
            throws Exception {
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(seed.getBytes(), enc);
        return new String(result, "utf-8");
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, zeroIv);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, zeroIv);
        return cipher.doFinal(encrypted);
    }

    /**
     * 把字符串内容转换成十六进制内容
     *
     * @param txt 内容
     * @return 十六进制内容
     */
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    /**
     * 十六进制内容转换成字符串内容
     *
     * @param hex 十六进制内容
     * @return 字符串内容
     */
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    /**
     * 把十六进制内容转换成字节数组
     *
     * @param hexString 十六进制内容
     * @return 字节数组
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    /**
     * 把字节数组转换成十六进制内容
     *
     * @param buf 字节数组
     * @return 十六进制内容
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
