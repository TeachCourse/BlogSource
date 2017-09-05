package cn.teachcourse.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.teachcourse.R;

public class BundleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
    }

    public void onClick(View view) {
        resolveService();
    }

    /**
     * 隐式Intent启动符合条件的Service
     */
    private void resolveService() {
        Intent intent = new Intent();
        intent.setAction(MyConstants.INTENT_ACTION+"launch");
        intent.addCategory(MyConstants.INTENT_CATEGORY+"launch.service");
        intent.setDataAndType(Uri.EMPTY, "cn/Bundle2Service");
        Bundle bundle = new Bundle();
        bundle.putString("description", getResources().getString(R.string.bundle_process_communication_service));
        intent.putExtras(bundle);
        try {
            startService(intent);
        } catch (Exception e) {
            Toast.makeText(this, "未找到符合要求的Service", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 隐式Intent启动符合条件的Activity
     */
    private void resolveActivity() {
        Intent intent = new Intent();
        intent.setAction(MyConstants.INTENT_ACTION+"launch");
        intent.addCategory(MyConstants.INTENT_CATEGORY+"launch");
        intent.setDataAndType(Uri.EMPTY, "cn/Bundle2Activity");
        Bundle bundle = new Bundle();
        bundle.putString("description", getResources().getString(R.string.bundle_process_communication));
        intent.putExtras(bundle);
        ComponentName name = intent.resolveActivity(getPackageManager());
        if (name != null) {
            startActivity(intent);
            return;
        }
        Toast.makeText(this, "未找到符合要求的activity", Toast.LENGTH_SHORT).show();
    }

    /**
     * 启动指定包名的应用MainActivity
     *
     * @param packageName
     */
    private void launchIntentForPackage(String packageName) {
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        startActivity(intent);
    }

    /**
     * 获取指定包名的应用上下文
     *
     * @param packageName
     * @return
     */
    private Context getContext(String packageName) {
        try {
            Context otherContext = this.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            return otherContext;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
