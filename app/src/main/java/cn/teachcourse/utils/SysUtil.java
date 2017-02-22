package cn.teachcourse.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;

public class SysUtil {
	/**
	 * 得到版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// Log.e(TAG, e.getMessage());
		}
		return verCode;
	}

	/**
	 * 得到版本名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo.versionName;
	}

	public static String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat/sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static String getUniqueId(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = "";
		uniqueId = deviceUuid.toString().replace("-", "_");

		Log.d("SYSUTIL", "uniqueId:" + uniqueId);
		return uniqueId;
	}

	/**
	 * 获取当前系统的版本号
	 * 
	 * @return
	 */
	public static int getAndroidSDKInt() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		return currentapiVersion;
	}

	/**
	 * 获取手机牌子
	 * 
	 * @return 手机牌子
	 */
	public static String getPhoneBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return 手机型号
	 */
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取Android手机的版本号 android2.3
	 * 
	 * @return 返回android版本号
	 */
	public static String getAndroidVersionNumber() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 检测当前网络是否连接可用
	 * @author TeachCourse博客
	 * 
	 * @return true|false
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isNetWorkWifi(Context context) {
		if (isNetworkAvailable(context)) {
			ConnectivityManager conManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (conManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/** 调用系统发送短信 */
	public static void sendMessage(Context context, String smsto) {

		Uri uri = Uri.parse("smsto:" + smsto);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		context.startActivity(it);
	}

	/** 调用系统打电话 */
	public static void call(Context context, String tel) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + tel.replace("-", "")));
		context.startActivity(intent);

	}

	// 隐藏软键盘
	public static void hiddenSoftInput(Activity context) {
		if (context.getCurrentFocus() != null) {

			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(context.getCurrentFocus()
					.getWindowToken(), 0); // 强制隐藏键盘
			context.getCurrentFocus().clearFocus();
		}
	}
}
