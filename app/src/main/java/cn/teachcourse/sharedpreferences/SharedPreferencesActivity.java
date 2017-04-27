package cn.teachcourse.sharedpreferences;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.utils.AESWrapper;
import cn.teachcourse.utils.HexUtil;

import static cn.teachcourse.R.id.input_content_et;

public class SharedPreferencesActivity extends BaseActivity {
    private static final String TAG = "SharedPreferencesActivity";
    private static final String CONTENT = "content";
    boolean isHasData = false;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private AESWrapper mAESWrapper;
    private EditText et;
    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        preferences = getSharedPreferences("cn.teachcourse.demo", Context.MODE_PRIVATE);
        editor = preferences.edit();
        mAESWrapper = new AESWrapper();
        initView();
        addEvent();
        initPermission();
        removeFile(CONTENT);
    }

    private void initView() {
        initCommon(getWindow().getDecorView());
        et = (EditText) findViewById(input_content_et);
        tv = (TextView) findViewById(R.id.display_content_tv);
        btn = (Button) findViewById(R.id.save_to_xml_btn);
        String content = getPackageName();
//        String content = "{\"tokenId\":\"0bf933de7712c412f1697de886da8e7e97797c40\",\"id\":2431,\"nickName\":\"小明\",\"realName\":\"\",\"phone\":\"18270000000\",\"sex\":\"M\",\"address\":\"中国广东省广州市白云区沿江中路351号\",\"longitude\":\"113.276545\",\"latitude\":\"23.121409\",\"idCard\":\"\",\"integral\":43,\"imgPath\":\"\\/userfiles\\/userInfo\\/2431\\/1487575421167.jpg\",\"userType\":\"2\",\"integralCard\":\"801506160594\",\"workStatus\":\"2\",\"deviceToken\":\"Ams205FzRA1dzRIISNrwN4Mb7AEWyLZCwgxFH7jAE-FS\",\"inviteCode\":\"MZni2q\",\"undoneOrderNum\":0,\"notEvaluateNum\":0,\"successOrderNum\":0,\"cancelList\":[{\"id\":94,\"label\":\"下错单\",\"value\":\"1\",\"type\":\"cancel_reason\",\"desciption\":\"取消原因\",\"sort\":1},{\"id\":95,\"label\":\"协商取消\",\"value\":\"2\",\"type\":\"cancel_reason\",\"desciption\":\"取消原因\",\"sort\":2},{\"id\":96,\"label\":\"无回收人员上门\",\"value\":\"3\",\"type\":\"cancel_reason\",\"desciption\":\"取消原因\",\"sort\":3}]}";
        et.setText(content);

    }

    private void addEvent() {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = et.getText().toString().trim();
                if (!TextUtils.isEmpty(content) && !isHasData) {
                    try {
                        isHasData = true;
                        btn.setText("读出数据");
                        byte[] b=mAESWrapper.encryptData(content.getBytes());
                        storeDataEncryptedWithSecureKey(b);

                        writeToFile(CONTENT,b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        byte[] b=new byte[encryptedData.length];
                        readBytesFromFile(CONTENT,b);
                        mAESWrapper.decryptData(b);
                        content=new String(b);
                        if (!TextUtils.isEmpty(content) && isHasData) {
                            isHasData = false;
                            tv.setText(content);
                            editor.remove(CONTENT).commit();
                            btn.setText("保存数据");
                        } else {
                            Toast.makeText(SharedPreferencesActivity.this, "先输入要保存内容", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*******************************************************************************/
    private byte[] getEncInSecByte(String content) throws UnsupportedEncodingException {
        byte[] b;
        Log.d(TAG, "onClick: 加密前字符长度：" + content.length());
        b = content.getBytes("ISO-8859-1");
        Log.d(TAG, "onClick: 加密前字节长度：" + b.length);
        b = mAESWrapper.encryptData(b);
        return b;
    }

    @NonNull
    private byte[] getDecInSecByte(String content) throws UnsupportedEncodingException {
        byte[] b = content.getBytes("ISO-8859-1");
        b = mAESWrapper.decryptData(b);
        return b;
    }

    private void storeEncInSecStr(byte[] b) throws UnsupportedEncodingException {
        String content;
        content = new String(b, "ISO-8859-1");
        Log.d(TAG, "onClick: 加密后字节长度：" + b.length);
        editor.remove(CONTENT);
        editor.putString(CONTENT, content).commit();
        Log.d(TAG, "onClick: 加密后字符长度：" + content.length());
        et.setText(preferences.getString(CONTENT, ""));
    }

    private String showDecInSecStr(byte[] b) throws UnsupportedEncodingException {
        String content;
        Log.d(TAG, "onClick: 解密前字节长度：" + b.length);
        content = new String(b, "ISO-8859-1");
        Log.d(TAG, "onClick: 解密后字符长度：" + content.length());
        return content;
    }

    /*******************************************************************************/

    private byte[] getEncBytes(String content) {
        byte[] b = encryptData(content.getBytes(), retrieveIv(), deriveKeySecurely(getPackageName(), KEY_SIZE));
//                    writeToFile("loginJson", b);
        storeDataEncryptedWithSecureKey(b);
        return b;
    }

    private byte[] getDecBytes(String content) {
        byte[] b = null;
//                    byte[] b = new byte[retrieveEncryptedData().length];
//                    readBytesFromFile("loginJson", b);
        try {
            b = content.getBytes("ISO-8859-1");
            Log.d(TAG, "onClick: 取出" + content.getBytes("ISO-8859-1").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//                    b = decryptData(retrieveEncryptedData(), retrieveIv(), deriveKeySecurely(getPackageName(), KEY_SIZE));
        b = decryptData(b, retrieveIv(), deriveKeySecurely(getPackageName(), KEY_SIZE));
        return b;
    }

    /**
     * @param data
     * @return
     */
    private String encrypt(String data) {
        byte[] b = encryptData(data.getBytes(), retrieveIv(), obtainSecretKey(getPackageName(), KEY_SIZE));
        return new String(b);
    }

    private String decrypt(String data) {
        byte[] b = decryptData(data.getBytes(), retrieveIv(), obtainSecretKey(getPackageName(), KEY_SIZE));
        return new String(b);
    }

    /***
     * 手动生成一个SecretKey
     */
    private SecretKey obtainSecretKey(String password, int keySize) {
        /* Store these things on disk used to derive key later: */
        int iterationCount = 1000;
        int saltLength = 32; // bytes; should be the same sizeas the output (256 / 8 = 32)
        int keyLength = keySize * 8; // 256-bits for AES-256, 128-bits for AES-128, etc
        byte[] salt; // Should be of saltLength

   /* When first creating the key, obtain a salt with this: */
        SecureRandom random = new SecureRandom();
        salt = new byte[saltLength];
        random.nextBytes(salt);

   /* Use this to derive the key from the password: */
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        byte[] keyBytes = new byte[0];
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1");
            keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    /**
     * Example use of a key derivation function, derivating a key securely from a password.
     */
    private SecretKey deriveKeySecurely(String password, int keySizeInBytes) {
        // Use this to derive the key from the password:
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), retrieveSalt(),
                100 /* iterationCount */, keySizeInBytes * 8 /* key size in bits */);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Deal with exceptions properly!", e);
        }
    }

    private byte[] encryptOrDecrypt(
            byte[] data, SecretKey key, byte[] iv, boolean isEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, key,
                    new IvParameterSpec(iv));
            return cipher.doFinal(data);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("This is unconceivable!", e);
        }
    }

    private byte[] encryptedData = null;

    private byte[] retrieveEncryptedData() {
        Log.e(TAG, "retrieveEncryptedData: " + encryptedData.length);
        return encryptedData;
    }

    private void storeDataEncryptedWithSecureKey(byte[] encryptedData) {
        // Mock implementation.
        this.encryptedData = encryptedData;
    }

    private byte[] encryptData(byte[] data, byte[] iv, SecretKey key) {
        return encryptOrDecrypt(data, key, iv, true);
    }

    private byte[] decryptData(byte[] data, byte[] iv, SecretKey key) {
        return encryptOrDecrypt(data, key, iv, false);
    }

    private byte[] retrieveIv() {
        byte[] iv = new byte[IV_SIZE];
        // Ideally your data should have been encrypted with a random iv. This creates a random iv
        // if not present, in order to encrypt our mock data.
        return iv;
    }

    private byte[] retrieveSalt() {
        // Salt must be at least the same size as the key.
        byte[] salt = new byte[KEY_SIZE];
        // Create a random salt if encrypting for the first time, and save it for future use.
        return salt;
    }

    /**
     * 加密的字节数组写入缓存文件
     *
     * @param fileName
     * @param bytes
     */
    private void writeToFile(String fileName, byte[] bytes) {
        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't write to " + fileName, e);
        }
    }

    /**
     * 读取缓存文件的加密数据并存入字节数组中
     *
     * @param fileName
     * @param bytes
     */
    private void readBytesFromFile(String fileName, byte[] bytes) {
        try (FileInputStream fis = openFileInput(fileName)) {
            int numBytes = 0;
            while (numBytes < bytes.length) {
                int n = fis.read(bytes, numBytes, bytes.length - numBytes);
                if (n <= 0) {
                    throw new RuntimeException("Couldn't read from " + fileName);
                }
                numBytes += n;
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from " + fileName, e);
        }
    }

    private static final int IV_SIZE = 16;
    private static final int KEY_SIZE = 32;

    @Override
    public String getUrl() {
        return null;
    }

    private static final int REQUEST_CODE_PERMISSION = 0x110;

    private void initPermission() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (isSecondRequest)
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x11);
                    else
                        Toast.makeText(this, "数据写入应用权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    private boolean fileExists(String fileName) {
        File file = new File(getFilesDir(), fileName);
        return file.exists();
    }

    private void removeFile(String fileName) {
        File file = new File(getFilesDir(), fileName);
        file.delete();
    }
}
