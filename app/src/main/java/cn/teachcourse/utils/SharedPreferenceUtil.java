package cn.teachcourse.utils;

/*
 @author TeachCourse
 @date �����ڣ�2015-11-11
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SharedPreferenceUtil {
	private final static String PREFERENCE_NAME = "com.sinolvc.recovery";
	private static SharedPreferences preferences;
	private static Editor editor;

	public static void initPreference(Context context) {
		preferences = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

	public static void putInt(String key, int value) {
		editor.putInt(key, value).commit();
	}

	public static int getInt(String key, int defValue) {
		return preferences.getInt(key, defValue);
	}

	public static void putLong(String key, long value) {
		editor.putLong(key, value).commit();
	}

	public static long getLong(String key, long defValue) {
		return preferences.getLong(key, defValue);
	}

	public static void putString(String key, String value) {
		try {
			editor.putString(key, AESUtil.encrypt(value)).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getString(String key, String defValue) {

		try {
			return AESUtil.decrypt(preferences.getString(key, defValue));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return preferences.getString(key, defValue);
	}

	public static void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value).commit();
	}

	public static boolean getBoolean(String key, boolean defValue) {
		return preferences.getBoolean(key, defValue);
	}

	public static boolean remove(String key) {
		return editor.remove(key).commit();
	}

	public static boolean contains(String key) {
		return preferences.contains(key);
	}
}
