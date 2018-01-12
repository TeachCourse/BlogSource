package cn.teachcourse.packages;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

import static android.support.v4.content.FileProvider.getUriForFile;

public class PackageInfoActivity extends BaseActivity {
    private static final String TAG = PackageInfoActivity.class.getName();
    private static final String PATH = "/demo.apk";
    private File file;//下载apk文件保存路径
    private TextView mTextView;//显示版本和包信息
    private String archiveFilePath;
    private ProgressDialog mProgressDialog;//显示下载进度条
    private Downloader mDownloader;//下载器
    private String apkPath = "http://121.15.220.153/imtt.dd.qq.com/16891/518096D97EBE5888B390B854668C8A86.apk?mkey=587c4a68d293078c&f=8d5d&c=0&fsname=com.qzone_7.1.1.288_98.apk&csr=4d5s&p=.apk";

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, PackageInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_info);

        initView();
        initData();

    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        initButton(getWindow().getDecorView());
        mTextView = (TextView) findViewById(R.id.package_info_tv);
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("正在读取文件。。。");
        mDownloader = new Downloader(this);
        mProgressDialog.setMax(mDownloader.totalCount);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initPermissionForReadOrWrite();
    }

    /**
     * 读取sdcard请求码
     **/
    private static final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 0x111;

    private void initPermissionForReadOrWrite() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_OR_WRITE);
        } else {
            loadPath();
        }
    }

    /**
     * 下载服务器的apk文件到本地
     */
    private void loadPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED)) {
            Toast.makeText(this, "未检测到SDcard，请检查是否插入SDcard", Toast.LENGTH_SHORT).show();
            return;
        }
        File rootPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        file = new File(rootPath, PATH);
        if (file.exists()) {
            file.delete();
        }
        if (Build.VERSION.SDK_INT > 23) {
            /**Android 7.0以上的方式**/
            Uri contentUri = getUriForFile(this, getString(R.string.file_provider), file);
            grantUriPermission(getPackageName(), contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        try {
            URL url = new URL(apkPath);
            new LoadAPKTask().execute(new URL[]{url});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION_READ_OR_WRITE == requestCode) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (isSecondRequest)
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_OR_WRITE);
                    else
                        Toast.makeText(this, "权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
                    break;
                case PackageManager.PERMISSION_GRANTED:
                    loadPath();
                    break;
            }
        }
    }

    @Override
    public String getUrl() {
        return null;
    }

    /**
     * 嵌套内部类LoadAPKTask，下载文件
     */
    private class LoadAPKTask extends AsyncTask<URL, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(URL... params) {
            try {
                InputStream is = Downloader.getInputStream(params[0]).getInputStream();
                long flag = mDownloader.loadFile(is, file.getAbsolutePath());
                int i = mDownloader.totalSize;
                int count = mDownloader.totalCount;
                int percent = (int) ((i / (float) count) * 100);
                if (flag != -1) {
                    publishProgress(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mDownloader.totalCount;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            setProgressPercent(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            String path = file.getAbsolutePath();
            if (TextUtils.isEmpty(path)) {
                return;
            }
            archiveFilePath = path;
            refreshUI();
            showDialog("Downloaded " + result + " bytes");
            mProgressDialog.dismiss();
            openFile(file);
        }

    }

    /**
     * 读取进度
     *
     * @param integer
     */
    public void setProgressPercent(Integer integer) {
        mProgressDialog.setProgress(integer);
        if (integer == mDownloader.totalCount)
            mProgressDialog.dismiss();
    }

    /**
     * 提示框
     *
     * @param string
     */
    public void showDialog(String string) {
        Toast.makeText(PackageInfoActivity.this, string, Toast.LENGTH_LONG).show();
        Log.d(TAG, string);
    }

    /**
     * 打开本地apk文件
     *
     * @param file
     */
    private void openFile(File file) {
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    /**
     * 显示版本和包信息
     */
    private void refreshUI() {
        if (archiveFilePath == null) {
            return;
        }
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            String appName = pm.getApplicationLabel(appInfo).toString();
            String packageName = appInfo.packageName;  //得到安装包名称
            String version = info.versionName; //得到版本信息
            mTextView.setText("appName: " + appName + "\npackageName: " + packageName + "\nversion: " + version);
            List<ApplicationInfo> list = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                ApplicationInfo applicationInfo = (ApplicationInfo) iter.next();
                Log.d(TAG, applicationInfo.packageName);
            }
        }
    }

    /**
     * 刷新UI界面的消息处理
     */
    protected static final int REFRESH_UI = 0X110;
    protected static final int NOTIFY_UI = 0X111;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_UI:
                    int i = mDownloader.totalSize;
                    int count = mDownloader.totalCount;
                    int percent = (int) ((i / (float) count) * 100);
                    Log.d(TAG, "读取文件大小：" + i + " 百分比：" + percent);
                    setProgressPercent(i);
                    break;
                case NOTIFY_UI:
                    mProgressDialog.dismiss();
                    openFile(file);
                    break;
            }
        }
    };
    /**
     * 工作线程，下载
     */
    private Thread mThread = new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                HttpURLConnection mURL = Downloader.getInputStream(new URL(apkPath));
                InputStream is = mURL.getInputStream();
                long flag = mDownloader.loadFile(is, file.getAbsolutePath());
                if (flag != -1) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
    }
}
