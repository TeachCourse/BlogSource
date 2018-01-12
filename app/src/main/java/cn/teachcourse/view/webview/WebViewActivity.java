package cn.teachcourse.view.webview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private ImageView mError_iv;
    private WebView mWebview;
    private boolean isAll = false;
    private int width;
    private int height;
    private static final String URL = "http://teachcourse.cn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_web_view);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        initButton(getWindow().getDecorView());
        mWebview = (WebView) findViewById(R.id.web_view_id);
        mError_iv=(ImageView)findViewById(R.id.widget_iv);
        set(mWebview);
        addEvent();
    }

    private void addEvent() {
        findViewById(R.id.screen_btn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!isAll){
                    isAll = true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mWebview.getLayoutParams();
                    params.width = height;
                    params.height = width;
                    mWebview.setLayoutParams(params);
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isAll){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mWebview.getLayoutParams();
                params.width = width;
                params.height = height/3;
                mWebview.setLayoutParams(params);
                isAll = false;
            }else {
                mWebview.loadUrl("about:blank");
               finish();
            }
        }
        return false;
    }
    /**
     * 初始化数据
     */
    private void initData(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
    }
    /**
     *
     * @param webview
     */
    private void set(WebView webview) {

        webview.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        setWebChromeClient(webview, activity);
        setWebViewClient(webview, activity);
        webview.loadUrl(URL);
    }

    private void setWebChromeClient(WebView webview, final Activity activity) {
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }

        });
    }

    private void setWebViewClient(WebView webview, final Activity activity) {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                return false;
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mError_iv.setImageDrawable(activity.getResources().getDrawable(R.drawable.network_error));
                mWebview.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mError_iv.setImageDrawable(activity.getResources().getDrawable(R.drawable.network_error));
                mWebview.setVisibility(View.GONE);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                if (isReload)
                Toast.makeText(activity,"刷新成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (url.endsWith(".apk"))
                    loadManager(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(activity,"页面加载结束",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadManager(String url) {
    }

    @Override
    public String getUrl() {
        return null;
    }
}
