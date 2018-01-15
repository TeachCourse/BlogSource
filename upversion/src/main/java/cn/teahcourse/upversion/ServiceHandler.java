package cn.teahcourse.upversion;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by http://teachcourse.cn on 2018/1/5.
 */

public class ServiceHandler extends Handler {
    private static final int READY_TO_DOWNLOAD_APK = 0;
    private static final int FAIL_TO_DOWNLOAD_APK = 1;
    private static final int PROGRESS_TO_DOWNLOAD_APK = 2;
    private static final int COMPLETE_TO_DOWNLOAD_APK = 3;
    //定义个更新速率，避免更新通知栏过于频繁导致卡顿
    private float rate = .0f;

    private APKDownloadService downloadService;
    private APKDownloadService.DownloadCallback callback;

    ServiceHandler(APKDownloadService downloadService) {
        this.downloadService = downloadService;

    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case READY_TO_DOWNLOAD_APK: {

                callback.onPrepare();
            }
            break;

            case FAIL_TO_DOWNLOAD_APK: {

                callback.onFail((String) msg.obj);
                downloadService.stopSelf();
            }
            break;

            case PROGRESS_TO_DOWNLOAD_APK: {

                int progress =msg.arg1;
                callback.onProgress(progress);
            }
            break;

            case COMPLETE_TO_DOWNLOAD_APK: {

                File file = (File) msg.obj;
                callback.onComplete(file);
                downloadService.stopSelf();
            }
            break;
        }
    }

    void download(String url, APKDownloadService.DownloadCallback callback) {
        this.callback = callback;
        if (TextUtils.isEmpty(url)) {
            fail("下载路径错误");
            return;
        }
        sendEmptyMessage(READY_TO_DOWNLOAD_APK);
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = FAIL_TO_DOWNLOAD_APK;
                message.obj = e.getMessage();
                sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onSuccess(response);
            }
        });
    }

    private void onSuccess(Response response) {
        if (response.body() == null) {
            Message message = Message.obtain();
            message.what = FAIL_TO_DOWNLOAD_APK;
            message.obj = "下载错误";
            sendMessage(message);
            return;
        }
        InputStream is = null;
        byte[] buff = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            File file = createFile();
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buff)) != -1) {
                fos.write(buff, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                if (rate != progress) {
                    Message message = Message.obtain();
                    message.what = PROGRESS_TO_DOWNLOAD_APK;
                    message.arg1 = progress;
                    sendMessage(message);
                    rate = progress;
                }
            }
            fos.flush();
            Message message = Message.obtain();
            message.what = COMPLETE_TO_DOWNLOAD_APK;
            message.obj = file.getAbsoluteFile();
            sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 路径为根目录
     * 创建文件名称为 updateDemo.apk
     */
    private File createFile() {
        String root = Environment.getExternalStorageDirectory().getPath();
        String versionName = downloadService.getApplicationInfo().name;
        File file = new File(root, versionName + ".apk");
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载完成
     */
    private void fail(String msg) {
        downloadService.stopSelf();
    }

    public void onDestroy() {
        removeCallbacksAndMessages(null);
    }
}
