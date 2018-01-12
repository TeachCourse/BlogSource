package cn.teachcourse.uploadpic.util;

import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by postmaster@teachcourse.cn on 2017/3/4.
 */

public class CustomWebChromeClient extends WebChromeClient {
    private static final String TAG = "CustomWebChromeClient";
    private PhotoUtil pickPhotoUtil;

    public CustomWebChromeClient(PhotoUtil pickPhotoUtil) {
        this.pickPhotoUtil = pickPhotoUtil;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        pickPhotoUtil.promptDialog();
        PhotoUtil.mFilePathCallback = filePathCallback;
        Log.d(TAG, "onShowFileChooser: 被调用几次？");
        /**
         * 返回true，如果filePathCallback被调用；返回false，如果忽略处理
         */

        return true;
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback) {
        pickPhotoUtil.openFileManager();
        PhotoUtil.mFilePathCallback4 = filePathCallback;
        Log.d(TAG, "openFileChooser: 被调用几次？");

    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
        pickPhotoUtil.promptDialog();
        String title = acceptType;
        PhotoUtil.mFilePathCallback4 = filePathCallback;
    }

    /**
     * 过时的方法：openFileChooser
     */
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
        pickPhotoUtil.promptDialog();
        String title = acceptType;
        PhotoUtil.mFilePathCallback4 = filePathCallback;
    }
}
