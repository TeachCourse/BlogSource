package cn.teachcourse.nougat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

import static android.support.v4.content.FileProvider.getUriForFile;

public class WriteToReadActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "WriteToReadActivity";
    /**
     * 拍摄照片请求码
     **/
    private static final int REQUEST_CODE = 0x1;
    private static final int RESULT_CAPTURE_IMAGE = REQUEST_CODE + 1;
    private static final int REQUEST_CODE_GRAINT_URI = REQUEST_CODE + 2;
    /**
     * 显示本地待安装apk路径
     **/
    private TextView mPath_tv;
    /**
     * 点击按钮安装apk文件
     **/
    private Button mInstall_btn;
    /**
     * 点击按钮开始拍照
     **/
    private Button mTakPhoto_btn;
    /**
     * 本地apk文件路径
     **/
    private String mPath = "/mnt/sdcard/92Recycle-release.apk";
    /**
     * sdcard本地路径
     **/
    private String mSDCardPath;
    /**
     * File类表示本地sdcard根目录
     **/
    private File mFileRoot;
    private StorageManager mStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_read);
        initView();
        initData();
        addEvent();

    }

    private void initData() {
        String int_root = getFilesDir().getAbsolutePath();
        String app_cache = getCacheDir().getAbsolutePath();
        String ext_root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String ext_pub = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String ext_cache = getExternalCacheDir().getAbsolutePath();

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            /**公共目录：file:///storage/emulated/0/Download**/
//            mFileRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            mFileRoot = Environment.getExternalStorageDirectory();
        } else {
            Toast.makeText(this, "sdcard不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvent() {
        mInstall_btn.setOnClickListener(this);
        mTakPhoto_btn.setOnClickListener(this);
    }

    private void initView() {
        initCommon(getWindow().getDecorView());
        mPath_tv = (TextView) findViewById(R.id.apk_path_tv);
        mInstall_btn = (Button) findViewById(R.id.install_apk_btn);
        mTakPhoto_btn = (Button) findViewById(R.id.take_photo_btn);
        if (Build.VERSION.SDK_INT >= 11)
            mPath_tv.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));

        mPath_tv.setText(mPath);
    }

    @Override
    public String getUrl() {
        return "http://teachcourse.cn/2360.html";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.install_apk_btn:
                initPermissionForReadOrWrite();
                break;
            case R.id.take_photo_btn:
                initPermissionForCamera();
                break;
        }
    }

    /**
     * 读取sdcard请求码
     **/
    private static final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 0x111;

    private void initPermissionForReadOrWrite() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_OR_WRITE);
        } else {

            if (mFileRoot != null)
                installApk(mFileRoot);
            else
                installApk(new File(mPath));
        }
    }

    /**
     * 安装应用程序
     *
     * @param file apk文件存放的路径
     */
    private void installApk(File file) {
        file = new File(file, "download/");
        file = new File(file, "92Recycle-release.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > 23) {
            /**Android 7.0以上的方式**/
            Uri contentUri = getUriForFile(this, getString(R.string.install_apk_path), file);
            /**请求授予的下面这句话等同于：intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);**/
//            grantUriPermission("cn.teachcourse.demos", contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            /**Android 7.0以前的方式**/
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }


        startActivity(intent);
    }

    /**
     * 调用系统相机
     */
    private void goToTakePhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(mFileRoot, System.currentTimeMillis() + ".jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT > 23) {
                /**Android 7.0以上的方式**/
                Uri contentUri = getUriForFile(this, getString(R.string.install_apk_path), file);
                grantUriPermission(getPackageName(), contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                /**Android 7.0以前的方式**/
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            startActivityForResult(intent, RESULT_CAPTURE_IMAGE);
        } else {
            Toast.makeText(this, "sdcard不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统相机
     */
    private void takePhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT > 23) {
                /**Android 7.0以上的方式**/
                mStorageManager = this.getSystemService(StorageManager.class);
                StorageVolume storageVolume = mStorageManager.getPrimaryStorageVolume();
                Intent intent = storageVolume.createAccessIntent(Environment.DIRECTORY_DOWNLOADS);
                startActivityForResult(intent, REQUEST_CODE_GRAINT_URI);
            }
        } else {
            Toast.makeText(this, "sdcard不存在", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CAPTURE_IMAGE:
                if (resultCode == RESULT_OK)
                    Toast.makeText(this, "拍照成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "拍照失败", Toast.LENGTH_SHORT).show();
                break;
            case REQUEST_CODE_GRAINT_URI:
                updateDirectoryEntries(data.getData());
                Log.d(TAG, "onActivityResult: " + data.getData());
                break;

        }
    }

    private static final String[] DIRECTORY_SELECTION = new String[]{
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateDirectoryEntries(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));

        try (Cursor docCursor = contentResolver
                .query(docUri, DIRECTORY_SELECTION, null, null, null)) {
            while (docCursor != null && docCursor.moveToNext()) {
                mPath_tv.setText(docCursor.getString(docCursor.getColumnIndex(
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME)));
            }
        }

        try (Cursor childCursor = contentResolver
                .query(childrenUri, DIRECTORY_SELECTION, null, null, null)) {
            while (childCursor != null && childCursor.moveToNext()) {
                String fileName = childCursor.getString(childCursor.getColumnIndex(
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME));
                String mimeType = childCursor.getString(childCursor.getColumnIndex(
                        DocumentsContract.Document.COLUMN_MIME_TYPE));
                Log.d(TAG, "updateDirectoryEntries: " + fileName + "\n" + mimeType);
            }

        }
    }

    private static final int REQUEST_CODE_PERMISSION = 0x110;

    private void initPermissionForCamera() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
        } else {
            takePhoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
                    if (isSecondRequest)
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
                    else
                        Toast.makeText(this, "拍照权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
                    break;
                case PackageManager.PERMISSION_GRANTED:
                    takePhoto();
                    break;
            }
        } else if (REQUEST_CODE_PERMISSION_READ_OR_WRITE == requestCode) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (isSecondRequest)
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_OR_WRITE);
                    else
                        Toast.makeText(this, "数据写入应用权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
                    break;
                case PackageManager.PERMISSION_GRANTED:
                    if (mFileRoot != null)
                        installApk(mFileRoot);
                    else
                        installApk(new File(mPath));
                    break;
            }
        }
    }
}
