package cn.teahcourse.rebootlaunchapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS},0x001);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0x001:
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                Settings.System.putInt(getContentResolver(), "status_bar_disabled", 1);
                break;
        }
    }
}
