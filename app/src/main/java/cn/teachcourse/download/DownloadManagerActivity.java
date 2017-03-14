package cn.teachcourse.download;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

import static android.support.v4.content.FileProvider.getUriForFile;

public class DownloadManagerActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "DownloadManagerActivity";
    private Button mDownloadManager_btn;
    private TextView mShowInformation_tv;
    private ImageView mShowPic_iv;
    private DownloadManager mDownloadManager;
    private long mDownloadUniqueId;
    private DownloadManager.Request request;
    /**
     * 请求下载的url地址
     **/
    private String mUrl = "http://teachcourse.cn/";
    private String mFileName = "logo.png";
    /**
     * 存放下载任务的相关信息
     */
    private StringBuffer mStringBuffer;
    private String information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mStringBuffer = new StringBuffer();
        initView();
        initData();
        addEvent();
        /**不能同时注册多个广播**/
        registerReceiverCompleted();
//        registerReceiverOnClick();
    }


    /**
     * 请求写入本地数据权限
     */
    private static final int REQUEST_CODE = 0x110;
    private static final int REQUEST_CODE_PERMISSION = REQUEST_CODE + 1;

    private void initPermission() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (isSecondRequest)
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                    else
                        Toast.makeText(this, "数据写入权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
                    break;
                case PackageManager.PERMISSION_GRANTED:
                    start();
                    break;
            }
        }

    }

    /**
     * 设置请求下载的数据
     */
    private void initData() {
        //Request内部类配置新下载任务相关内容，比如：保存路径，WiFi或流量状态，下载通知栏样式
        request = new DownloadManager.Request(Uri.parse(mUrl + mFileName));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mFileName);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("正在下载应用程序");
        request.setDescription("92回收，就爱回收APP");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
    }

    /**
     * 下载任务的唯一标识ID，用于查询下载文件的相关信息
     */
    private void start() {
        mDownloadUniqueId = mDownloadManager.enqueue(request);
        mDownloadManager_btn.setText("正在下载。。。");
        mDownloadManager_btn.setClickable(false);
    }

    private void addEvent() {
        mDownloadManager_btn.setOnClickListener(this);
    }

    private void initView() {
        mDownloadManager_btn = (Button) findViewById(R.id.download_manager_btn);
        mShowInformation_tv = (TextView) findViewById(R.id.show_information_tv);
        mShowPic_iv = (ImageView) findViewById(R.id.show_pic_iv);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_manager_btn:
                initPermission();


                break;
            default:
                break;
        }
    }

    /**
     * 注册下载完成广播接收器，还可以注册其它监听器，比如：DownloadManager.ACTION_NOTIFICATION_CLICKED
     */
    private void registerReceiverCompleted() {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    /**
     * 注册点击下载通知栏接收器
     */
    private void registerReceiverOnClick() {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(mBroadcastReceiverOnClick, intentFilter);
    }

    /**
     * 接收下载完成广播
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (mDownloadUniqueId == reference) {
                query(reference);
                mShowInformation_tv.setText(information);
                mDownloadManager_btn.setText("点击下载");
                mDownloadManager_btn.setClickable(true);
            }
        }
    };
    /**
     * 接收点击通知栏广播，打开下载任务管理器
     */
    private BroadcastReceiver mBroadcastReceiverOnClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (mDownloadUniqueId == reference) {
                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
            }
        }
    };

    /**
     * 查询下载任务相关的信息，比如：文件名、文件大小、文件类型等
     *
     * @param reference
     */
    private void query(long reference) {
        DownloadManager.Query query = new DownloadManager.Query();
        /**指定查询条件**/
        query.setFilterById(reference);
        /**查询正在等待、运行、暂停、成功、失败状态的下载任务**/
        query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);

        Cursor cursor = mDownloadManager.query(query);
        if (cursor.moveToFirst()) {
            int fileId = cursor.getColumnIndex(DownloadManager.COLUMN_ID);
            int fileTitleId = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileDescriptionId = cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION);
            int fileTypeId = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE);
            int fileLengthId = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int fileUriId = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
            /**过时的方式：DownloadManager.COLUMN_LOCAL_FILENAME**/
            int fileNameId = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
            int statusCodeId = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int statusReasonId = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int downloadSizeId = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int lastModifiedTimeId = cursor.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
            int mediaUriId = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI);

            String id = cursor.getString(fileId);
            String fileTitle = cursor.getString(fileTitleId);
            String description = cursor.getString(fileDescriptionId);
            String type = cursor.getString(fileTypeId);
            String length = cursor.getString(fileLengthId);
            String statusCode = cursor.getString(statusCodeId);
            String statusReason = cursor.getString(statusReasonId);
            String downloadSize = cursor.getString(downloadSizeId);
            String modifiedTime = cursor.getString(lastModifiedTimeId);
            String mediaUri = cursor.getString(mediaUriId);
            String fileUri = cursor.getString(fileUriId);
            String fileName = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                openFile(type, Uri.parse(fileUri));
                fileName = Uri.parse(fileUri).getPath();
            } else {
                /**Android 7.0以上的方式：请求获取写入权限，这一步报错**/
                fileName = cursor.getString(fileNameId);
                openFile(type, Uri.parse(fileUri));
            }


            /**清空StringBuffer存储的数据**/
            mStringBuffer.delete(0, mStringBuffer.length());
            mStringBuffer.append("id：" + id + "\n");
            mStringBuffer.append("fileTitle：" + fileTitle + "\n");
            mStringBuffer.append("description：" + description + "\n");
            mStringBuffer.append("type：" + type + "\n");
            mStringBuffer.append("length：" + length + "\n");
            mStringBuffer.append("fileName：" + fileName + "\n");
            mStringBuffer.append("fileUri：" + fileUri + "\n");
            mStringBuffer.append("statusCode：" + statusCode + "\n");
            mStringBuffer.append("statusReason：" + statusReason + "\n");
            mStringBuffer.append("downloadSize：" + downloadSize + "\n");
            mStringBuffer.append("modifiedTime：" + modifiedTime + "\n");
            mStringBuffer.append("mediaUri：" + mediaUri + "\n");
            information = mStringBuffer.toString();


        }
        cursor.close();
    }

    /**
     * 根据文件的类型，指定可以打开的应用程序
     *
     * @param type
     * @param uri
     */
    private void openFile(String type, Uri uri) {
        if (type.contains("image/")) {
            try {
                ParcelFileDescriptor descriptor = getContentResolver().openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = descriptor.getFileDescriptor();
                /**下面这句话运行效果等同于：BitmapFactory.decodeFileDescriptor()**/
                Bitmap bitmap = BitmapFactory.decodeStream(getStreamByFileDescriptor(fileDescriptor));
//                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                mShowPic_iv.setVisibility(View.VISIBLE);
                mShowPic_iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过流的方式读取内容
     *
     * @param fileDescriptor
     * @return
     */
    private InputStream getStreamByFileDescriptor(FileDescriptor fileDescriptor) {
        return new FileInputStream(fileDescriptor);
    }

    /**往FileDescriptor中写入数据
     * @param fileDescriptor
     * @param content
     */

    private void writeData(FileDescriptor fileDescriptor, String content) {
        FileOutputStream fos = new FileOutputStream(fileDescriptor);
        try {
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**从FileDescriptor中读取数据
     * @param fileDescriptor
     * @return
     */
    private String readData(FileDescriptor fileDescriptor) {
        FileInputStream fis = new FileInputStream(fileDescriptor);
        byte[] b = new byte[1024];
        int read;
        String content=null;
        try {
            while ((read = fis.read(b)) != -1) {
                content = new String(b, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 删除或取消正在下载的任务
     *
     * @param reference 下载任务的唯一标识ID
     */
    private void remove(long reference) {
        mDownloadManager.remove(reference);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        } else if (mBroadcastReceiverOnClick != null) {
            unregisterReceiver(mBroadcastReceiverOnClick);
        }

    }
}
