package cn.teahcourse.baseutil;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionsUtils {
    private static final String TAG = "PermissionsUtils";
    public static int permissionCode = 0x13;

    public static boolean hasPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permission) {
        requestPermissions(activity, permission, permissionCode);
    }

    public static void requestPermissions(Activity activity, String[] permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, permission, requestCode);
        Log.d(TAG, "---------->requestCode=" + requestCode);
    }
}
