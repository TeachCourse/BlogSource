package cn.teachcourse.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teahcourse.baseutil.PermissionsUtils;
import cn.teahcourse.upversion.IContract;
import cn.teahcourse.upversion.impl.ViewPresenterImpl;

public class UpVersionActivity extends AppCompatActivity {
    private IContract.View mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upversion);
        String apkUrl = "http://a6.pc6.com/qsj5/92huishou.apk";

        //一、初始化Dialog
        mPresenter = new ViewPresenterImpl(this, apkUrl);
    }

    public void onClick(View view) {
        PermissionsUtils.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "正在下载新版本，请稍后", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsUtils.permissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {

                PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
                String local = pi.versionName;
                String newVersion = "2.0";
                //二、检查版本更新状态
                mPresenter.checkUpdate(local, newVersion);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            PermissionsUtils.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }


    @Override
    protected void onDestroy() {
        //三、如果开启后台下载服务，调用该方法解除绑定
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
