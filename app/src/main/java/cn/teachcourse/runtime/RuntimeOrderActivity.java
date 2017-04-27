package cn.teachcourse.runtime;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class RuntimeOrderActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RuntimeOrderActivity";
    private Button mUninstallApp_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_order);
        initView();
        addEvent();
    }

    private void addEvent() {
        mUninstallApp_btn.setOnClickListener(this);
    }

    private void initView() {
        mUninstallApp_btn = (Button) findViewById(R.id.uninstall_app_btn);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uninstall_app_btn:
                execShell("/system/bin/adb shell pm uninstall com.example.tangyangkai.myview");
                break;
        }
    }

    private boolean isUninstall(String name) {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/adb shell pm uninstall " + name);
            int status = process.waitFor();
            if (status == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 第一种执行方法
     *
     * @param cmd
     */
    public void execShell(String cmd) {
        try {
            //权限设置
            Process p = Runtime.getRuntime().exec("su");
            //获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            //将命令写入
            dataOutputStream.writeBytes(cmd);
            //提交命令
            dataOutputStream.flush();
            //关闭流操作
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 第二种执行方法
     *
     * @param command
     * @throws IOException
     */
    public void execCommand(String command) {
        Process proc = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec(command);
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
            }
            System.out.println(stringBuffer.toString());

        } catch (InterruptedException e) {
            System.err.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                proc.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
