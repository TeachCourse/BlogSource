package cn.teachcourse.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.teachcourse.common.BaseActivity;

/**
 * Created by 钊林IT on 2018/4/2.
 */

public class LifecycleHomePressedActivity extends BaseActivity {
    private static final String TAG = "LifecycleHomePressedAct";
    StringBuilder orientation_sb = new StringBuilder();

    TextView display_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);

        display_tv = new TextView(this);
        ll.addView(display_tv);

        final EditText edit = new EditText(this);
        edit.setHint(" Activity上按Home键时的生命周期");
        ll.addView(edit);

        //演示Activity上有Dialog按下Home键时的生命周期
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog dialog=null;
                if (dialog==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LifecycleHomePressedActivity.this)
                            .setMessage("我是一个Dialog弹窗，请按Home试试哈");
                    dialog = builder.create();
                }else{
                    dialog.dismiss();
                }
                dialog.show();
            }
        });

        //演示两个Activity 之间跳转时必然会执行的是哪几个方法？
        final Button button=new Button(this);
        button.setText("点我跳转喔");
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //第一种情况
                startActivity(new Intent(LifecycleHomePressedActivity.this,ChangeScreenStateActivity.class));
                //执行Activity A  生命周期：onCreate—>onStart—>onResume—>onPause，
                // 其次Activity B 生命周期：onCreate—>onStart—>onResume，
                // 再执行Activity A 生命周期：onSaveInstanceState—>onStop
            }
        });
        ll.addView(button);
        //第二种情况
        startActivity(new Intent(LifecycleHomePressedActivity.this,ChangeScreenStateActivity.class));
        //执行Activity A  生命周期：onCreate—>onStart—>onResume—>onPause，
        // 其次Activity B 生命周期：onCreate—>onStart—>onResume，
        // 再执行Activity A 生命周期：onSaveInstanceState—>onStop


        setContentView(ll);

        orientation_sb.delete(0, orientation_sb.length());
        String info = TAG+"----->onCreate\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String info = TAG+"----->onDestroy\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        String info = TAG+"----->onPause\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        String info = TAG+"----->onRestart\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String info = TAG+"----->onResume\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        String info = TAG+"----->onStart\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        String info = TAG+"----->onStop\n";
        orientation_sb.append(info);
        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onStop");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String info = TAG+"----->onRestoreInstanceState\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String info = TAG+"----->onSaveInstanceState\n";
        orientation_sb.append(info);

        display_tv.setText(orientation_sb.toString());
        System.out.println(TAG+"----->onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            String info = TAG+"----->onConfigurationChanged：现在是横屏转竖屏\n";
            orientation_sb.append(info);

            display_tv.setText(orientation_sb.toString());
            System.out.println("现在是横屏转竖屏");
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            String info = TAG+"----->onConfigurationChanged：现在是竖屏转横屏\n";
            orientation_sb.append(info);

            display_tv.setText(orientation_sb.toString());
            System.out.println("现在是竖屏转横屏");
        }
    }
}
