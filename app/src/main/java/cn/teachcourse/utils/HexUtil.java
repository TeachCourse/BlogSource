package cn.teachcourse.utils;

import android.util.Log;

/**
 * Created by postmaster@teachcourse.cn on 2017/2/23.
 */

public class HexUtil {
    private static final String TAG = "HexUtil";
    /**
     * 将普通字符串用16进制描述
     * 如"WAZX-B55SY6-S6DT5" 描述为："57415a582d4235355359362d5336445435"
     * */
    public static String strToHex(String str){
        byte[] bytes = str.getBytes();
        return bytesToHex(bytes);
    }

    /**将16进制描述的字符串还原为普通字符串
     * 如"57415a582d4235355359362d5336445435" 还原为："WAZX-B55SY6-S6DT5"
     * */
    public static String hexToStr(String hex){
        byte[] bytes=hexToBytes(hex);
        return new String(bytes);
    }


    /**16进制转byte[]*/
    public static byte[] hexToBytes(String hex){
        int length = hex.length() / 2;
        byte[] bytes=new byte[length];
        for(int i=0;i<length;i++){
            String tempStr=hex.substring(2*i, 2*i+2);//byte:8bit=4bit+4bit=十六进制位+十六进制位
            bytes[i]=(byte) Integer.parseInt(tempStr, 16);
        }
        return bytes;
    }

    /**byte[]转16进制*/
    public static String bytesToHex(byte[] bytes){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            int tempI=bytes[i] & 0xFF;//byte:8bit,int:32bit;高位相与.
            String str = Integer.toHexString(tempI);
            if(str.length()<2){
                sb.append(0).append(str);//长度不足两位，补齐：如16进制的d,用0d表示。
            }else{
                sb.append(str);
            }
        }
        return sb.toString();
    }
    /**
     * 和后台联调时使用
     * 该函数用于讲16进制数组转成String
     * 如密钥key为
     * uint8_t key[] =
     *         {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6,
     *          0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c}
     * 则格式化之后为"2b7e151628aed2a6abf7158809cf4f3c"
     * */
    public static String convertStr(String hexStr) {
        String[] kStrs = hexStr.split(",");
        String[] keyStrs = new String[kStrs.length];
        for (int i = 0; i < kStrs.length; i++) {
            String str = kStrs[i].trim().substring(2);
            keyStrs[i] = str;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : keyStrs) {
            sb.append(str);
        }
        return sb.toString().toUpperCase();
    }

}
