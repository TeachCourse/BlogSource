package cn.teahcourse.rebootlaunchapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @author TeachCourse
 * @Description: 带进度条的WebView
 */
@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {
    private ProgressBar progressbar;
    private Context context;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 5, 0, 0));

        Drawable drawable = context.getResources().getDrawable(
                R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);
        init();
    }

    private void init() {
        // 是否支持缩放,WebSettings设置参数
        WebSettings settings = getSettings();
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(false);//是否使用缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setUseWideViewPort(true); //设置内容自适应屏幕大小
        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 设置WebViewClient接受各种通知和请求
        setWebViewClient(new MyWebViewClient());
        // 设置WebChromeClient的实现方式，用于处理脚本对话框、图标、标题和进度条
        setWebChromeClient(new CustomWebChromeClient());
        setDownloadListener(new MyWebViewDownLoadListener());
    }

//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
//        lp.x = l;
//        lp.y = t;
//        progressbar.setLayoutParams(lp);
//        super.onScrollChanged(l, t, oldl, oldt);
//    }

    private class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(View.GONE);
            } else {
                if (progressbar.getVisibility() == View.GONE)
                    progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (webViewTitle != null)
                webViewTitle.showTitle(title);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        /**
         * 定义处理正在加载url的方式
         *
         * @param view
         * @param url
         * @return 当前浏览器处理正在加载的url，默认返回false；否则返回true
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            /**
             * 处理特殊的url地址，比如“tel:”
             */
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }

            return true;
        }

        @Override
        public void onReceivedSslError(final WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (webViewTitle!=null)
                webViewTitle.pageFinished();
        }
    }

    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimeType, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    public IWebViewTitle webViewTitle;

    public void setWebViewTitle(IWebViewTitle webViewTitle) {
        this.webViewTitle = webViewTitle;
    }

    public interface IWebViewTitle {
        void showTitle(String title);
        void pageFinished();
    }

}