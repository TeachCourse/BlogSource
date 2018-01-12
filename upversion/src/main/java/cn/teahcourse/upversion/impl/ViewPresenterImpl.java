package cn.teahcourse.upversion.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import cn.teahcourse.upversion.IContract;

/**
 * Created by http://teachcourse.cn on 2018/1/9.
 */

public class ViewPresenterImpl implements IContract.View {
    private Activity activity;
    private Dialog mDialog;
    private ServicePresenterImpl mPresenter;
    private String apkUrl;
    private ProgressDialog mProgressBar;

    public ViewPresenterImpl(Activity activity, String apkUrl) {
        this.activity = activity;
        this.apkUrl = apkUrl;
        mPresenter = new ServicePresenterImpl(this);
    }

    @Override
    public void showUpdate(final String version) {
        if (mDialog == null)
            mDialog = new AlertDialog.Builder(activity)
                    .setTitle("检测到有新版本")
                    .setMessage("当前版本:" + version)
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String url = apkUrl;
                            mPresenter.downApk(activity, url);
                        }
                    })
                    .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.setIgnore(version);
                        }
                    })
                    .create();

        //重写这俩个方法，一般是强制更新不能取消弹窗
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && mDialog != null && mDialog.isShowing();
            }
        });

        mDialog.show();
    }

    @Override
    public void showProgress(int progress) {
        if (mProgressBar == null) {
            mProgressBar = new ProgressDialog(activity);
            mProgressBar.setMax(100);
            mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressBar.show();
        }
        mProgressBar.setProgress(progress);
    }

    @Override
    public void showComplete() {
        mProgressBar.dismiss();
    }

    @Override
    public Intent install(File file) {
        Intent intent = getIntent(file);

        return intent;
    }


    @NonNull
    private Intent getIntent(File file) {
        String authority = activity.getApplicationContext().getPackageName() + ".fileProvider";
        Uri fileUri = FileProvider.getUriForFile(activity, authority, file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //7.0以上需要添加临时读取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        return intent;
    }

    @Override
    public void showFail(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getAppContext() {
        return activity.getApplicationContext();
    }

    @Override
    public void checkUpdate(String oldVersion, String newVersion) {
        mPresenter.checkUpdate(oldVersion, newVersion);
    }

    @Override
    public void onDestroy() {
        mPresenter.unbind(activity);
    }
}
