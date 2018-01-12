package cn.teahcourse.baseutil;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by postmaster@teachcourse.cn on 2017/2/23.
 */

public class AESWrapper {
    /**
     * Key的长度
     **/
    private static final int KEY_SIZE = 16;
    private static final String ALGORITHMS = "SHA1PRNG";
    private static final String PROVIDER = "Crypto";
    private SecretKey secretKey;

    public AESWrapper() {
        getKey(getKeyGen(ALGORITHMS, PROVIDER));
    }

    /**
     * 第一步： 准备钥匙的材料
     *
     * @param algorithms
     * @param provider
     * @return
     */
    private KeyGenerator getKeyGen(String algorithms, String provider) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            if (provider == null || "".equals(provider))
                keyGenerator.init(KEY_SIZE * 8, SecureRandom.getInstance(algorithms, provider));
            else
                keyGenerator.init(KEY_SIZE * 8, SecureRandom.getInstance(algorithms));

            return keyGenerator;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 第二步：生成秘钥
     *
     * @param keyGenerator
     * @return
     */
    private Key getKey(KeyGenerator keyGenerator) {
        SecretKey key = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");

        return this.secretKey = key;
    }

    /**
     * 第三步：开始加密或解密
     *
     * @param data
     * @param key
     * @param isEncrypt
     * @return
     */
    private byte[] encryptOrDecrypt(byte[] data, SecretKey key, boolean isEncrypt) {
        try {
            SecretKeySpec keySpec=new SecretKeySpec(key.getEncoded(),"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(data);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("This is unconceivable!", e);
        }
    }

    private SecretKey getSecretKey() {
        return secretKey;
    }

    public byte[] encryptData(byte[] data) {
        return encryptOrDecrypt(data, getSecretKey(), true);
    }

    public byte[] decryptData(byte[] data) {
        return encryptOrDecrypt(data, getSecretKey(), false);
    }


}
