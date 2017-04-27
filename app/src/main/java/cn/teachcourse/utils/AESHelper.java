package cn.teachcourse.utils;

import android.support.annotation.NonNull;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by postmaster@teachcourse.cn on 2017/2/23.
 */

public class AESHelper {
    public static String encrypt(String content, String pwd, String charSet) {
        try {
            byte[] byteContent = content.getBytes(charSet);

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(pwd.getBytes()));

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

            byte[] buf = cipher.doFinal(byteContent);
            return getEncHexStr(buf);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param buf
     * @return
     */
    private static String getEncHexStr(byte[] buf) {
        //byte[]转16进制
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /***
     * @param content 16进制的字符串
     * @param pwd
     * @param charSet
     * @return
     */
    public static String decrypt(String content, String pwd, String charSet) {
        try {
            byte[] byteContent = new byte[content.length() / 2];
            if (content.length() < 1) {
                return null;
            }
            //将16进制转换为二进制
            for (int i = 0; i < content.length() / 2; i++) {
                int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
                byteContent[i] = (byte) (high * 16 + low);
            }

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(pwd.getBytes()));

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

            byte[] buf = cipher.doFinal(byteContent);
            return new String(buf, charSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String layer = AESHelper.encrypt("编程ABCDefgh~！@#$%^&*()|'?>.<;", "123", "utf-8");
        System.out.println(layer);
        String plain = AESHelper.decrypt(layer, "123", "utf-8");
        System.out.println(plain);
    }

}
