package cn.teachcourse.deviceId;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by lenovo on 2016/5/1.
 */
public class UniqueDevId {
    public static final int ANDROID_ID = 1;
    public static final int DEVICE_ID = 2;
    public static final int MAC_ADDRESS_ID = 3;
    public static final int SERIAL_NUMBER_ID = 4;
    public static final int INSTALLTION_ID = 5;
    public static final int PSEUDO_UNIQUE_ID = 6;

    private Activity mContext;

    public UniqueDevId(Activity mContext) {
        this.mContext = mContext;
    }

    public String getDevId(int id) {
        String devId = null;
        switch (id) {
            /**
             *设备第一次启动时产生和存储的64bit的一个数
             */
            case ANDROID_ID:
                devId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                break;
            /**
             * 根据不同的手机设备返回IMEI，MEID或者ESN码
             */
            case DEVICE_ID:
                devId=((TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                break;
            /**
             *通过手机的Wifi或者蓝牙设备获取MAC ADDRESS作为DEVICE ID
             */
            case MAC_ADDRESS_ID:
                WifiManager wm = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                devId = wm.getConnectionInfo().getMacAddress();
                break;
            /**
             *获取SIM卡的设备编号
             */
            case SERIAL_NUMBER_ID:
                devId=((TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
                break;
            /**
             * 通过在程序安装后第一次运行生成一个ID实现的
             */
            case INSTALLTION_ID:
                devId = Installation.getId(mContext);
                break;
            /**
             * 这个在任何Android手机中都有效
             */
            case PSEUDO_UNIQUE_ID:
                devId = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
                break;
        }
        if (devId != null) {
            return devId;
        }
        return null;
    }


}
