package cn.teahcourse.upversion.impl;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import java.util.List;
import java.util.Locale;

import cn.teahcourse.upversion.IContract;
import cn.teahcourse.upversion.R;

import static android.os.Process.killProcess;

/**
 * Created by http://teachcourse.cn on 2018/1/5.
 */

public class NotifyPresenterImpl implements IContract.INotify {
    private Context context;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private static final int NOTIFY_ID = 0;

    public NotifyPresenterImpl(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("开始下载")
                .setContentText("正在连接服务器")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
    }

    @Override
    public void prepare() {
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
    }

    @Override
    public void complete(String title, String msg, Intent intent) {
        if (intent == null) return;
        if (onFront()) {
            cancel();
            context.startActivity(intent);
            //弹出安装窗口把原程序关闭。
            //避免安装完毕点击打开时没反应
            killProcess(android.os.Process.myPid());
        } else {
            PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext()
                    , 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pIntent)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setProgress(0, 0, false)
                    .setDefaults(Notification.DEFAULT_ALL);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(NOTIFY_ID, notification);
        }
    }

    @Override
    public void progress(String title,int progress) {
        mBuilder.setContentTitle(title)
                .setContentText(String.format(Locale.CHINESE, "%d%%", progress))
                .setProgress(100, progress, false)
                .setWhen(System.currentTimeMillis());
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFY_ID, notification);
    }

    @Override
    public void fail(String title, String msg) {
        mBuilder.setContentTitle(title)
                .setContentText(msg);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFY_ID, notification);
    }

    @Override

    public void cancel() {
        mNotificationManager.cancel(NOTIFY_ID);
    }

    @Override
    public void clear() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
            mNotificationManager = null;
        }
        if (mBuilder != null)
            mBuilder = null;
    }

    /**
     * 是否运行在用户前面
     */
    private boolean onFront() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null || appProcesses.isEmpty())
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName()) &&
                    appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
