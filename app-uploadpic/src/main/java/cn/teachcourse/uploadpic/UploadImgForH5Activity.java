package cn.teachcourse.uploadpic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import java.io.File;

import cn.teachcourse.uploadpic.util.CustomWebChromeClient;
import cn.teachcourse.uploadpic.util.GetPathFromUri4kitkat;
import cn.teachcourse.uploadpic.util.PhotoUtil;

public class UploadImgForH5Activity extends AppCompatActivity {
    private static final String TAG = "UploadImgForH5Activity";
    private WebView mWebView;
    private CustomWebChromeClient mWebChromeClient;
    /**
     * 加载asset文件存放的H5页面
     **/
    private String url = "file:///android_asset/uploadImgForH5.html";
    /**
     * Android 6.0以上版本，需求添加运行时权限申请；否则，可能程序崩溃
     */
    private static final int REQUEST_CODE_PERMISSION = 0x1;
    private PhotoUtil mPhotoUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img_for_h5);
        initView();
        initData();
        initPermissionForCamera();
    }


    private void initPermissionForCamera() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writeFlag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag || PackageManager.PERMISSION_GRANTED != writeFlag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                boolean shouldShowRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
                if (shouldShowRequest) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
                }
            }
            if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                boolean shouldShowRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (shouldShowRequest)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
            }
        }
    }

    private void initData() {
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.loadUrl(url);
    }

    private void initView() {
        mPhotoUtil = new PhotoUtil(this);
        mWebView = (WebView) findViewById(R.id.widget_wv);
        mWebChromeClient = new CustomWebChromeClient(mPhotoUtil);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
         * 处理页面返回或取消选择结果
         */
        switch (requestCode) {
            case PhotoUtil.REQUEST_FILE_PICKER:

                pickPhotoResult(resultCode, data);
                break;
            case PhotoUtil.REQUEST_CODE_PICK_PHOTO:

                pickPhotoResult(resultCode, data);
                break;
            case PhotoUtil.REQUEST_CODE_TAKE_PHOTO:

                takePhotoResult(resultCode);
                break;
            case PhotoUtil.REQUEST_CODE_PREVIEW_PHOTO:

                mPhotoUtil.cancelFilePathCallback(PhotoUtil.photoPath);
                break;
            default:

                break;
        }
    }

    private void pickPhotoResult(int resultCode, Intent data) {
        if (PhotoUtil.mFilePathCallback != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                PhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});
                /*
                 * 将路径赋值给常量photoFile4，记录第一张上传照片路径
                 */
                PhotoUtil.photoPath = path;

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /*
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PhotoUtil.mFilePathCallback.onReceiveValue(null);
                PhotoUtil.mFilePathCallback = null;
            }
            /*
             * 针对API 19之前的版本
             */
        } else if (PhotoUtil.mFilePathCallback4 != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                PhotoUtil.mFilePathCallback4.onReceiveValue(uri);
                /*
                 * 将路径赋值给常量photoFile
                 */
                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /*
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PhotoUtil.mFilePathCallback4.onReceiveValue(null);
                PhotoUtil.mFilePathCallback4 = null;
            }
        }
    }

    private void takePhotoResult(int resultCode) {
        if (PhotoUtil.mFilePathCallback != null) {
            if (resultCode == RESULT_OK) {
                String path = PhotoUtil.photoPath;
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                PhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /*
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PhotoUtil.mFilePathCallback.onReceiveValue(null);
                PhotoUtil.mFilePathCallback = null;
            }
            /*
             * 针对API 19之前的版本
             */
        } else if (PhotoUtil.mFilePathCallback4 != null) {
            if (resultCode == RESULT_OK) {
                String path = PhotoUtil.photoPath;
                File file = new File(path);
                Uri uri = Uri.fromFile(file);
                PhotoUtil.mFilePathCallback4.onReceiveValue(uri);

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /*
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PhotoUtil.mFilePathCallback4.onReceiveValue(null);
                PhotoUtil.mFilePathCallback4 = null;
            }
        }
    }

}
