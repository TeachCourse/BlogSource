package cn.teachcourse.view.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teahcourse.baseutil.LogUtil;

public class JSWebViewActivity extends BaseActivity {
    private WebView mWebView;
    private Button mButton;
    public boolean isModify = false;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, JSWebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsweb_view);
        initView();
    }

    /**
     * 初始化布局控件
     */
    @SuppressLint("JavascriptInterface")
    private void initView() {
        initButton(getWindow().getDecorView());
        mWebView = (WebView) findViewById(R.id.webView);
        mButton = (Button) findViewById(R.id.modify_btn);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setDomStorageEnabled(true);
        /**
         * Java Object与JavaScript交互
         */
        mWebView.addJavascriptInterface(this, "myObj");

//        mWebView.loadUrl("file:///android_asset/js_interative.html");
        mWebView.loadUrl("file:///android_asset/launcher_myapp.html");
        /**
         * 验证为什么application ID不同于package name提示ClassNotFoundException
         */
        String callingApp = getPackageManager().getNameForUid(Binder.getCallingUid());
//        mWebView.loadUrl("file:///android_res/raw/note.html");

        LogUtil.d(callingApp);
        LogUtil.d(getPackageName());
        /**
         * JavaScript与Java Object交互
         */
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:modifyContent()");
                isModify = !isModify;
                Toast.makeText(JSWebViewActivity.this, "" + isModify, Toast.LENGTH_SHORT).show();
                startBrowser();
            }
        });
    }

    private void startBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_APP_BROWSER);
        startActivity(intent);
    }

    private boolean isLogin;

    /**
     * 在Android 4.2以前不需要添加注释：@JavascriptInterface，WebView和JS可以正常交互；在Android 4.2以后需要手动添加注释：@JavascriptInterface，WebView和JS交互成功，否则提示 “ uncaught typeerror object has no method ”
     */
    @JavascriptInterface
    public void finishMyself() {
        Toast.makeText(this, "关闭当前Activity", Toast.LENGTH_LONG).show();
        finish();
    }

    @JavascriptInterface
    public void dispatchJavaMethod(String name) {
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public boolean isLogin() {
        if (isLogin) {
            return true;
        }
        return false;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
