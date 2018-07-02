package com.liqi.utils.encoding;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES算术加密(向4.2版本以上支持)
 * * <p>
 * 只适合android本地加密
 * </p>
 *
 * @author LiQi
 */
public class AndroidAESEncryptor {
    private final static String HEX = "0123456789ABCDEF";
    private static String CODING = "UTF-8";

    /**
     * 128-加密
     *
     * @param seed      加密钥匙
     * @param cleartext 要加密内容
     * @param coding    编码
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt128(String seed, String cleartext, String coding)
            throws Exception {
        byte[] rawKey = getRawKey(128, seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes(TextUtils.isEmpty(coding) ? CODING : coding));
        return toHex(result);
    }

    /**
     * 192-加密
     *
     * @param seed      加密钥匙
     * @param cleartext 要加密内容
     * @param coding    编码
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt192(String seed, String cleartext, String coding)
            throws Exception {
        byte[] rawKey = getRawKey(192, seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes(TextUtils.isEmpty(coding) ? CODING : coding));
        return toHex(result);
    }

    /**
     * 256-加密
     *
     * @param seed      加密钥匙
     * @param cleartext 要加密内容
     * @param coding    编码
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt256(String seed, String cleartext, String coding)
            throws Exception {
        byte[] rawKey = getRawKey(256, seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes(TextUtils.isEmpty(coding) ? CODING : coding));
        return toHex(result);
    }

    /**
     * 128-加密
     *
     * @param seed      加密钥匙
     * @param cleartext 要加密内容
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt128(String seed, String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey(128, seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes(CODING));
        return toHex(result);
    }

    /**
     * 192-加密
     *
     * @param seed      加密钥匙
     * @param cleartext 要加密内容
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt192(String seed, String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey(192, seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    /**
     * 256-加密
     *
     * @param seed      加密钥匙
     * @param cleartext 要加密内容
     * @return 加密之后的内容
     * @throws Exception 加密失败异常
     */
    public static String encrypt256(String seed, String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey(256, seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    /**
     * 128-解密
     *
     * @param seed      解密钥匙
     * @param encrypted 加密内容
     * @param coding    编码
     * @return
     * @throws Exception
     */
    public static String decrypt128(String seed, String encrypted, String coding)
            throws Exception {
        byte[] rawKey = getRawKey(128, seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, TextUtils.isEmpty(coding) ? CODING : coding);
    }

    /**
     * 192-解密
     *
     * @param seed      解密钥匙
     * @param encrypted 加密内容
     * @param coding    编码
     * @return
     * @throws Exception
     */
    public static String decrypt192(String seed, String encrypted, String coding)
            throws Exception {
        byte[] rawKey = getRawKey(192, seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, TextUtils.isEmpty(coding) ? CODING : coding);
    }

    /**
     * 256-解密
     *
     * @param seed      解密钥匙
     * @param encrypted 加密内容
     * @param coding    编码
     * @return
     * @throws Exception
     */
    public static String decrypt256(String seed, String encrypted, String coding)
            throws Exception {
        byte[] rawKey = getRawKey(256, seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, TextUtils.isEmpty(coding) ? CODING : coding);
    }

    /**
     * 128-解密
     *
     * @param seed      解密钥匙
     * @param encrypted 加密内容
     * @return
     * @throws Exception
     */
    public static String decrypt128(String seed, String encrypted)
            throws Exception {
        byte[] rawKey = getRawKey(128, seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, "utf-8");
    }

    /**
     * 192-解密
     *
     * @param seed      解密钥匙
     * @param encrypted 加密内容
     * @return
     * @throws Exception
     */
    public static String decrypt192(String seed, String encrypted)
            throws Exception {
        byte[] rawKey = getRawKey(192, seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    /**
     * 256-解密
     *
     * @param seed      解密钥匙
     * @param encrypted 加密内容
     * @return
     * @throws Exception
     */
    public static String decrypt256(String seed, String encrypted)
            throws Exception {
        byte[] rawKey = getRawKey(256, seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, "utf-8");
    }

    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(int pattern, byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
        SecureRandom sr;
        if (android.os.Build.VERSION.SDK_INT > 23) {  // Android  6.0 以上
            sr = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
        } else if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        kgen.init(pattern, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
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
    // 增加  CryptoProvider  类

    private static class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        private CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
