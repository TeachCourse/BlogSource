package cn.teachcourse.screenadapter;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import cn.teahcourse.baseutil.StatusBarUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.actionBar));
        StatusBarUtil.hideFakeStatusBarView(this);

    }

    public void autoLayout(View view){
        AutoLayoutActivity.start(this);
    }

    public void simpleLayout(View view){
        SimpleLayoutActivity.start(this);
    }
}
