package cn.teachcourse.view.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.scancode.CaptureActivity;

public class ZXingJSWebViewActivity extends BaseActivity {
    private static final String TAG = "ZXingJSWebViewActivity";
    private ImageView mPersonCentral_iv;//个人中心
    private ImageView mRadarScan_iv;//扫描图标
    private TextView mCommonHeadTitle_tv;//标题
    private WebView mWebView;//加载网页的WebView，实现JS交互
    private MyOnClickListener mOnClickListener;//实现OnClickListener接口的内部类
    private Intent mIntent;
    private String url="http://teachcourse.cn";
    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, ZXingJSWebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_jsweb_view);
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        initButton(getWindow().getDecorView());
        mPersonCentral_iv = (ImageView) findViewById(R.id.person_central_iv);
        mRadarScan_iv = (ImageView) findViewById(R.id.radar_scan_iv);
        mWebView = (WebView) findViewById(R.id.webView);
        mCommonHeadTitle_tv = (TextView) findViewById(R.id.common_head_title_tv);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIntent = getIntent();
        String flag=mIntent.getStringExtra("flag");
        if (mIntent != null) {
            if ("scanZXing".equals(flag)) {
                mPersonCentral_iv.setImageResource(R.drawable.navigationbar_back);
                mCommonHeadTitle_tv.setText(getResources().getString(R.string.scan_zxing_login));
                mRadarScan_iv.setVisibility(View.GONE);
                mWebView.addJavascriptInterface(this, "myObj");
                mWebView.loadUrl("file:///android_asset/zxing_js_success.html");
            }else

                mWebView.loadUrl(url);
        }
    }
    @JavascriptInterface
    public void finishMyself(){
        setResult(RESULT_OK,new Intent(this,CaptureActivity.class));
        finish();
    }

    /**
     * 初始化点击事件
     */
    private void initEvent() {
        mOnClickListener = new MyOnClickListener();
        mRadarScan_iv.setOnClickListener(mOnClickListener);
        mPersonCentral_iv.setOnClickListener(mOnClickListener);
    }

    @Override
    public String getUrl() {
        return null;
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.radar_scan_iv:
                    intent = new Intent(ZXingJSWebViewActivity.this, CaptureActivity.class);
                    break;
                case R.id.person_central_iv:
                    finishMyself();
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    }
    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("http://")||url.startsWith("https://")){
                return false;
            }
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;

        }
    }
}
