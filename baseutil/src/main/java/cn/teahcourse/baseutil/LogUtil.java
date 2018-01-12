package cn.teahcourse.baseutil;


/**
 * Created by postmaster@teachcourse.cn on 2017/3/15.
 */

public class LogUtil {

    public static final boolean isDebug = true;
    private static final String TAG = "AllDemos-debug------->";

    /**
     * Debug输出Log日志
     **/
    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            android.util.Log.d(tag, msg);
        }
    }

    /**
     * Error输出Log日志
     **/
    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            android.util.Log.e(tag, msg);
        }
    }

    /**
     * Info输出Log日志
     **/
    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            android.util.Log.i(tag, msg);
        }
    }

    /**
     * Send a VERBOSE log message
     */
    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            android.util.Log.v(tag, msg);
        }
    }
}
