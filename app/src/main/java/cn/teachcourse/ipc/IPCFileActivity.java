package cn.teachcourse.ipc;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.teachcourse.R;
import cn.teahcourse.baseutil.LogUtil;

public class IPCFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipcfile);
    }
    /**
     * 读取sdcard请求码
     **/
    private static final int REQUEST_CODE_PERMISSION_READ_OR_WRITE = 0x111;
    private void initPermission() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED != flag) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_OR_WRITE);
        } else {
            persistToFile();
        }
    }
    public void onClick(View view){
        initPermission();
    }

    /**
     * 启动符合条件的Activity
     * @param view
     */
    public void launchB(View view){
        Intent intent = new Intent();
        intent.setAction(MyConstants.INTENT_ACTION+"launchB");
        intent.addCategory(MyConstants.INTENT_CATEGORY+"launchB");
        ComponentName name = intent.resolveActivity(getPackageManager());
        if (name != null) {
            startActivity(intent);
            return;
        }
    }

    /**
     * 将内容写入缓存文件
     */
    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fos=null;
                try {
                    File dir = new File(MyConstants.FILE_PATH);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File cachedFile = new File(MyConstants.CACHE_CONTENT_PATH);
                    fos=new FileOutputStream(cachedFile);
                    byte[] b="hello world".getBytes();
                    fos.write(b);
                    LogUtil.d("hello world 成功写入本地缓存。。。");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    MyUtils.close(fos);
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION_READ_OR_WRITE == requestCode) {
            switch (grantResults[0]) {
                case PackageManager.PERMISSION_DENIED:
                    boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (isSecondRequest)
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_OR_WRITE);
                    else
                        Toast.makeText(this, "数据写入应用权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
                    break;
                case PackageManager.PERMISSION_GRANTED:
                    persistToFile();
                    break;
            }
        }
    }
}
