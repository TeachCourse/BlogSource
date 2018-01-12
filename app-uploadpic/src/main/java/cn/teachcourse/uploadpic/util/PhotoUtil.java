package cn.teachcourse.uploadpic.util;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Toast;

import java.io.File;

import cn.teachcourse.uploadpic.PreviewImgActivity;
import cn.teachcourse.uploadpic.R;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * Created by postmaster@teachcourse.cn on 2016/12/20.
 */

public class PhotoUtil {
    private static final String TAG = "PhotoUtil";
    private AppCompatActivity activity;
    /**
     * 记录上传照片路径：第一张photoPath，第二张photoPath2
     */
    public static String photoPath;
    public static String photoPath2;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0x10;
    public static final int REQUEST_FILE_PICKER = 0x11;//请求调起文件管理器
    public static final int REQUEST_CODE_PICK_PHOTO = 0x12;
    public static final int REQUEST_CODE_PREVIEW_PHOTO = 0x13;
    /**
     * 为什么需要设置声明两个ValueCallback对象？
     * mFilePathCallback，在Android 4.0以上API版本中被赋值，回调onShowFileChooser方法
     * mFilePathCallback4，在Android 4.0以下API版本中被赋值，回调openFileChooser方法
     */
    public static ValueCallback mFilePathCallback;
    public static ValueCallback mFilePathCallback4;

    public PhotoUtil(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * 显示弹窗
     */
    public void promptDialog() {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(activity).builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("手机拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                goToTakePhoto();

                            }
                        })
                .addSheetItem("手机相册", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goForPicFile();
                            }
                        });
        actionSheetDialog.addSheetItem("图片预览", ActionSheetDialog.SheetItemColor.Blue,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showTheBigImage();
                    }
                });

        actionSheetDialog.show();
        /**
         * 设置点击“取消”按钮监听，目的取消mFilePathCallback回调，可以重复调起弹窗
         */
        actionSheetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelFilePathCallback(photoPath);
            }
        });
    }

    private void cancelFilePathCallback() {
        if (mFilePathCallback4 != null) {
            mFilePathCallback4.onReceiveValue(null);
            mFilePathCallback4 = null;
        } else if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    public void cancelFilePathCallback(String path) {
        if (path != null) {
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            if (PhotoUtil.mFilePathCallback4 != null) {
                PhotoUtil.mFilePathCallback4.onReceiveValue(uri);
                PhotoUtil.mFilePathCallback4 = null;
            } else if (PhotoUtil.mFilePathCallback != null) {
                PhotoUtil.mFilePathCallback.onReceiveValue(new Uri[]{uri});
                PhotoUtil.mFilePathCallback = null;
            }
        } else {
            cancelFilePathCallback();
        }
    }

    /**
     * 预览图片
     */
    private void showTheBigImage() {
        if (photoPath != null) {
            String url = photoPath;
            PreviewImgActivity.start(activity, url);
        } else {
            cancelFilePathCallback();
        }

    }

    /**
     * 调用系统相机
     */
    private void goToTakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        photoFile = new File(photoFile, System.currentTimeMillis() + ".jpg");
        photoPath = photoFile.getAbsolutePath();
        if (Build.VERSION.SDK_INT > 23) {
            /**Android 7.0以上的方式**/
            String authority = activity.getApplicationContext().getPackageName() + ".fileProvider";
            Uri contentUri = getUriForFile(activity, authority, photoFile);
            activity.grantUriPermission(activity.getPackageName(), contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            /**Android 7.0以下的方式**/
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        }
        activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }


    /**
     * 访问系统相册
     */

    private void goForPicFile() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

    /**
     * 打开文件管理器
     */
    public void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_FILE_PICKER);
    }
}
