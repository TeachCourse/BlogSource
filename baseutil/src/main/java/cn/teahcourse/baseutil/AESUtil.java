package cn.teahcourse.baseutil;

/*
 @author TeachCourse
 @date �����ڣ�2015-11-11
 */

import android.util.Log;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String TAG = "AESUtil";
    private static final String SEED = "cn.teachcourse";
    private static final String ALGORITHMS = "SHA1PRNG";
    private static final String PROVIDER = "Crypto";

    public static String encrypt(String clearText) throws Exception {
        byte[] rawkey = getRawKey(SEED.getBytes());
        byte[] result = encrypt(rawkey, clearText.getBytes());
        Provider[] p=Security.getProviders();
        for (Provider pro:p)
            Log.d(TAG, "encrypt: "+pro);
        return toHex(result);

    }

    public static String decrypt(String encrypted) throws Exception {
        byte[] rawKey = getRawKey(SEED.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = null;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance(ALGORITHMS, PROVIDER);
        } else {
            sr = SecureRandom.getInstance(ALGORITHMS);
        }
        sr.setSeed(seed);
        kgen.init(128, sr);
        SecretKey sKey = kgen.generateKey();
        byte[] raw = sKey.getEncoded();

        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * 将16进制字符串转为二进制：解密
     *
     * @param hexString
     * @return
     */
    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    /**
     * 将byte[]转为16进制字符串：加密
     *
     * @param buf
     * @return
     */
    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt((b >> 4) & 0x0f));
        sb.append(HEX.charAt(b & 0x0f));
    }

}
