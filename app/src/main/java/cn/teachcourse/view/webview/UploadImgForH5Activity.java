package cn.teachcourse.view.webview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.utils.GetPathFromUri4kitkat;
import cn.teachcourse.utils.PickPhotoUtil;

public class UploadImgForH5Activity extends BaseActivity {
    private static final String TAG = "UploadImgForH5Activity";
    private WebView mWebView;
    private MyWebChromeClient mWebChromeClient;
    /**加载asset文件存放的H5页面**/
    private String url="file:///android_asset/uploadImgForH5.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img_for_h5);
        initView();
        initData();
        initPermissionForCamera();
    }

    /**
     * Android 6.0以上版本，需求添加运行时权限申请；否则，可能程序崩溃
     */
    private static final int REQUEST_CODE_PERMISSION = 0x110;
    private void initPermissionForCamera() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
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
            }
        }
    }
    private void initData() {
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    private void initView() {
        initCommon(getWindow().getDecorView());
        mWebView= (WebView) findViewById(R.id.widget_wv);
        mWebChromeClient=new MyWebChromeClient(new PickPhotoUtil(this));
    }

    @Override
    public String getUrl() {
        return "http://teachcourse.cn/2224.html";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理页面返回或取消选择结果
         */
        switch (requestCode) {
            case PickPhotoUtil.REQUEST_FILE_PICKER:
                pickPhotoResult(resultCode, data);
                break;
            case PickPhotoUtil.REQUEST_CODE_PICK_PHOTO:
                pickPhotoResult(resultCode, data);
                break;
            case PickPhotoUtil.REQUEST_CODE_TAKE_PHOTO:
                takePhotoResult(resultCode);

                break;
            case PickPhotoUtil.REQUEST_CODE_PREVIEW_PHOTO:
                cancelFilePathCallback();
                break;
            default:

                break;
        }
    }

    private void pickPhotoResult(int resultCode, Intent data) {
        if (PickPhotoUtil.mFilePathCallback != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});
                /**
                 * 将路径赋值给常量photoFile4，记录第一张上传照片路径
                 */
                PickPhotoUtil.photoPath = path;

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback = null;
            }
            /**
             * 针对API 19之前的版本
             */
        } else if (PickPhotoUtil.mFilePathCallback4 != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(uri);
                /**
                 * 将路径赋值给常量photoFile
                 */
                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback4 = null;
            }
        }
    }

    private void takePhotoResult(int resultCode) {
        if (PickPhotoUtil.mFilePathCallback != null) {
            if (resultCode == RESULT_OK) {
                String path = PickPhotoUtil.photoPath;
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback = null;
            }
            /**
             * 针对API 19之前的版本
             */
        } else if (PickPhotoUtil.mFilePathCallback4 != null) {
            if (resultCode == RESULT_OK) {
                String path = PickPhotoUtil.photoPath;
                Uri uri = Uri.fromFile(new File(path));
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(uri);

                Log.d(TAG, "onActivityResult: " + path);
            } else {
                /**
                 * 点击了file按钮，必须有一个返回值，否则会卡死
                 */
                PickPhotoUtil.mFilePathCallback4.onReceiveValue(null);
                PickPhotoUtil.mFilePathCallback4 = null;
            }
        }
    }

    private void cancelFilePathCallback() {
        if (PickPhotoUtil.mFilePathCallback4 != null) {
            PickPhotoUtil.mFilePathCallback4.onReceiveValue(null);
            PickPhotoUtil.mFilePathCallback4 = null;
        } else if (PickPhotoUtil.mFilePathCallback != null) {
            PickPhotoUtil.mFilePathCallback.onReceiveValue(null);
            PickPhotoUtil.mFilePathCallback = null;
        }
    }

}
