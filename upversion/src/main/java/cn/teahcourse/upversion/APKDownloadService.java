package cn.teahcourse.upversion;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by http://teachcourse.cn on 2018/1/5.
 */

public class APKDownloadService extends Service {
    private DownloadBinder binder = new DownloadBinder();
    private ServiceHandler serviceHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceHandler = new ServiceHandler(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        serviceHandler.onDestroy();
    }

    /**
     * 和activity通讯的binder
     */
    public class DownloadBinder extends Binder {
        public APKDownloadService getService() {
            return APKDownloadService.this;
        }
    }

    /**
     * 开始下载apk
     */
    public void load(String url, DownloadCallback callback) {
        serviceHandler.download(url, callback);
    }

    /**
     * 销毁时清空一下对notify对象的持有
     */
    @Override
    public void onDestroy() {
        serviceHandler.onDestroy();
        super.onDestroy();
    }

    /**
     * 定义一下回调方法
     */
    public interface DownloadCallback {
        void onPrepare();

        void onProgress(int progress);

        void onComplete(File file);

        void onFail(String msg);

        Intent install(File file);
    }
}
