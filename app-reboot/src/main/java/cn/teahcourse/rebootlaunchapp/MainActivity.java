package cn.teahcourse.rebootlaunchapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_PERMISSION_BOOT_COMPLETED = 0x02;
    private static final String BASE_URL = "http://test.92recycle.com/static/terminal/index.html";
    private ProgressWebView mProgressWebView;
    private TextView mTitle_tv;
    private LinearLayout mBack_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
        initView();
    }

    private void initView() {
        mProgressWebView = (ProgressWebView) findViewById(R.id.load_web_wv);
        mTitle_tv = (TextView) findViewById(R.id.head_view_title_tv);
        mBack_ll = (LinearLayout) findViewById(R.id.head_view_back_ll);
        mProgressWebView.setWebViewTitle(new ProgressWebView.IWebViewTitle() {
            @Override
            public void showTitle(String title) {
                mTitle_tv.setText(title);
            }

            @Override
            public void pageFinished() {
                if (mProgressWebView.canGoBack()) {
                    mBack_ll.setVisibility(View.VISIBLE);
                } else {
                    mBack_ll.setVisibility(View.GONE);
                }
            }
        });
        mBack_ll.setOnClickListener(this);
    }


    private void initPermission() {
        String[] permissions = new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.WRITE_SETTINGS, Manifest.permission.INTERNET};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION_BOOT_COMPLETED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_BOOT_COMPLETED:
                bootCompleted(grantResults[0]);
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    Settings.System.putInt(getContentResolver(), "status_bar_disabled", 1);
                if (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                    mProgressWebView.loadUrl(BASE_URL);
                break;
            default:
                break;
        }
    }

    /**
     * 检查重启后权限
     *
     * @param grantResult
     */
    private void bootCompleted(int grantResult) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
            boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_BOOT_COMPLETED);
            if (isSecondRequest)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, REQUEST_CODE_PERMISSION_BOOT_COMPLETED);
            else
                Toast.makeText(this, "权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 禁用返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mProgressWebView.canGoBack()) {
            return true;//不执行父类点击事件
        } else if (mProgressWebView.canGoBack()) {
            mProgressWebView.goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_view_back_ll:

                if (mProgressWebView.canGoBack())
                    mProgressWebView.goBack();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webDestroy();
    }

    private void webDestroy() {
        if (mProgressWebView != null) {
            ViewParent parent = mProgressWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mProgressWebView);
            }
            mProgressWebView.removeAllViews();
            mProgressWebView.destroy();
            mProgressWebView = null;
        }
    }


}
