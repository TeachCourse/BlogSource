package cn.teachcourse.enums;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class MainActivity extends BaseActivity {
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        initButton(getWindow().getDecorView());
    }

    @Override
    public String getUrl() {
        return null;
    }
}
