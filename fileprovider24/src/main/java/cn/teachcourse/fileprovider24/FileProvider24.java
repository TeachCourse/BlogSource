package cn.teachcourse.fileprovider24;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * Created by http://teachcourse.cn on 2018/5/17.
 */

public class FileProvider24 {

    public static Uri getUriForFile(Context context, File file) {
        String authority = context.getPackageName() + ".fileProvider";
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= 24) {
            /**Android 7.0以上的方式**/
            contentUri = android.support.v4.content.FileProvider.getUriForFile(context, authority, file);
        } else {
            /**Android 7.0以前的方式**/
            contentUri = Uri.fromFile(file);
        }
        return contentUri;
    }

    public static void grantUriPermission(Context context, Intent intent, Uri contentUri, int flags) {
        if (Build.VERSION.SDK_INT >= 24) {
            /**Android 7.0以上的方式**/
            String packageName = context.getPackageName();
            context.grantUriPermission(packageName, contentUri, flags);
            intent.addFlags(flags);
        }
    }

    public static void grantUriReadPermission(Context context, Intent intent, Uri contentUri) {
        if (Build.VERSION.SDK_INT >= 24) {
            /**Android 7.0以上的方式**/
            String packageName = context.getPackageName();
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public static void grantUriWritePermission(Context context, Intent intent, Uri contentUri) {
        if (Build.VERSION.SDK_INT >= 24) {
            /**Android 7.0以上的方式**/
            String packageName = context.getPackageName();
            context.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}
