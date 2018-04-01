package cn.teachcourse.app;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.view.Builder;

/**
 * Created by 钊林IT on 2018/4/1.
 */

public class ChangeScreenStateActivity extends BaseActivity {
    StringBuilder orientation_sb = new StringBuilder();

    TextView display_tv;
    TextView displayKeyboard_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);

        display_tv = new TextView(this);
        ll.addView(display_tv);

        EditText edit = new EditText(this);
        edit.setHint("点击查看键盘对生命周期的影响");
        ll.addView(edit);

        displayKeyboard_tv = new TextView(this);
        ll.addView(displayKeyboard_tv);

        setContentView(ll);

        orientation_sb.delete(0, orientation_sb.length());
        String info = "Activity----->onCreate\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String info = "Activity----->onDestroy\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        String info = "Activity----->onPause\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        String info = "Activity----->onRestart\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String info = "Activity----->onResume\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        String info = "Activity----->onStart\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        String info = "Activity----->onStop\n";
        orientation_sb.append(info);
        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onStop");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String info = "Activity----->onRestoreInstanceState\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String info = "Activity----->onSaveInstanceState\n";
        orientation_sb.append(info);

        displayKeyboard_tv.setText(orientation_sb.toString());

        display_tv.setText(orientation_sb.toString());
        System.out.println("Activity----->onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            String info = "Activity----->onConfigurationChanged：现在是横屏转竖屏\n";
            orientation_sb.append(info);

            displayKeyboard_tv.setText(orientation_sb.toString());

            display_tv.setText(orientation_sb.toString());
            System.out.println("现在是横屏转竖屏");
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            String info = "Activity----->onConfigurationChanged：现在是竖屏转横屏\n";
            orientation_sb.append(info);

            displayKeyboard_tv.setText(orientation_sb.toString());

            display_tv.setText(orientation_sb.toString());
            System.out.println("现在是竖屏转横屏");
        }
    }
}
