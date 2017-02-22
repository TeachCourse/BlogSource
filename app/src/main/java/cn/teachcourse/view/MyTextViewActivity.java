package cn.teachcourse.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.teachcourse.R;
import cn.teachcourse.view.define.MyTextView;

public class MyTextViewActivity extends AppCompatActivity {
    private static final String TAG = "MyTextViewActivity";
    private MyTextView myTextView;
    public static void start(Context context)
    {
        start(context, null);
    }

    public static void start(Context context, Intent extras)
    {
        Intent intent = new Intent();
        intent.setClass(context, MyTextViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_text_view);
        initView();
    }

    private void initView() {
        myTextView = (MyTextView) findViewById(R.id.my_textview);
        myTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e(TAG, "onTouch: 触摸事件。。。。");
                return false;
            }
        });

        myTextView.setmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof MyTextView) {
                    MyTextView view=(MyTextView)v;
                    Log.e(TAG, "onClick: " +view.getText());
                }
            }
        });
    }
}
