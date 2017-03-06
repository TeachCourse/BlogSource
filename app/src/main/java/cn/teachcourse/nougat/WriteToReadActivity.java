package cn.teachcourse.nougat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
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
    private static final int RESULT_CAPTURE_IMAGE = 0x1;
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
     * File类表示本地apk文件路径
     **/
    private File mFileAPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_read);
        initView();
        initData();
        addEvent();

    }

    private void initData() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mSDCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileAPK = new File(mSDCardPath, "92Recycle-release.apk");
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

            if (mFileAPK != null)
                installApk(mFileAPK);
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
        /**Android 7.0以上的方式**/
        Uri contentUri = getUriForFile(this, getString(R.string.install_apk_path), file);
        grantUriPermission(getPackageName(), contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        /**Android 7.0以前的方式**/
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * 调用系统相机
     */
    private void goToTakePhoto() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(mSDCardPath, System.currentTimeMillis() + ".jpg");
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
        }
    }

    private static final int REQUEST_CODE_PERMISSION = 0x110;

    private void initPermissionForCamera() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
        } else {
            goToTakePhoto();
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
                    goToTakePhoto();
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
                    if (mFileAPK != null)
                        installApk(mFileAPK);
                    else
                        installApk(new File(mPath));
                    break;
            }
        }
    }
}
