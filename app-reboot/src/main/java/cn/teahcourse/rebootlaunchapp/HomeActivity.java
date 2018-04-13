package cn.teahcourse.rebootlaunchapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION_WRITE_SETTINGS = 0x01;
    private static final String URL = "http://m.jd.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initPermission();
        final WebView webView = (WebView) findViewById(R.id.load_web_wv);
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.loadUrl(URL);
    }

    private void initPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_SETTINGS, Manifest.permission.INTERNET};
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE_PERMISSION_WRITE_SETTINGS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_WRITE_SETTINGS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Settings.System.putInt(getContentResolver(), "status_bar_disabled", 1);
                break;
        }
    }
}
