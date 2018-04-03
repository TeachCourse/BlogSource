package cn.teahcourse.rebootlaunchapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
    }

    private static final int REQUEST_CODE_PERMISSION_BOOT_COMPLETED = 0x111;

    private void initPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, REQUEST_CODE_PERMISSION_BOOT_COMPLETED);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS},0x001);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_BOOT_COMPLETED:
                bootCompleted(grantResults);
                break;
            case 0x001:
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    Settings.System.putInt(getContentResolver(), "status_bar_disabled", 1);
                break;
        }
    }

    /**
     * 检查重启后权限
     * @param grantResult
     */
    private void bootCompleted(int[] grantResult) {
        if (grantResult[0] == PackageManager.PERMISSION_DENIED) {
            boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
            if (isSecondRequest)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, REQUEST_CODE_PERMISSION_BOOT_COMPLETED);
            else
                Toast.makeText(this, "权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
        }
    }
}
