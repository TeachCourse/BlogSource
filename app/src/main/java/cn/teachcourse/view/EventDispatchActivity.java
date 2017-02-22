package cn.teachcourse.view;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cn.teachcourse.R;

import static cn.teachcourse.R.id.dispatch_ll;

public class EventDispatchActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "EventDispatchActivity";
    private RelativeLayout mParentView_rl;
    private ImageView mDispatch_iv;
    private ImageView mDispatchEvent_iv;
    private Button mDispatchEvent_btn;
    private LinearLayout mDispatch_ll;
    private LinearLayout mDispatchEvent_ll;
    /**
     * flag默认值false
     */
    public static boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);
        initView();
        addEvent();
    }

    private void addEvent() {
//        setClickListener();
//        setTouchListener();
    }

    private void setTouchListener() {
        mParentView_rl.setOnTouchListener(this);
        mDispatch_iv.setOnTouchListener(this);
        mDispatch_ll.setOnTouchListener(this);
        mDispatchEvent_iv.setOnTouchListener(this);
        mDispatchEvent_ll.setOnTouchListener(this);
        mDispatchEvent_btn.setOnTouchListener(this);

    }

    private void setClickListener() {
        mParentView_rl.setOnClickListener(this);
        mDispatch_iv.setOnClickListener(this);
        mDispatch_ll.setOnClickListener(this);
        mDispatchEvent_iv.setOnClickListener(this);
        mDispatchEvent_ll.setOnClickListener(this);
        mDispatchEvent_btn.setOnClickListener(this);
    }

    private void initView() {
        mParentView_rl = (RelativeLayout) findViewById(R.id.activity_event_dispatch);
        mDispatch_iv = (ImageView) findViewById(R.id.dispatch_iv);
        mDispatchEvent_iv = (ImageView) findViewById(R.id.dispatch_event_iv);
        mDispatch_ll = (LinearLayout) findViewById(R.id.dispatch_ll);
        mDispatchEvent_ll = (LinearLayout) findViewById(R.id.dispatch_event_ll);
        mDispatchEvent_btn = (Button) findViewById(R.id.dispatch_event_btn);

        /**
         * 指定拦截父容器名称
         */
        ((MyImageView)mDispatchEvent_iv).setmParentView(mParentView_rl);
        ((MyButton)mDispatchEvent_btn).setmParentView(mParentView_rl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_event_dispatch:
                Log.d(TAG, "onClick: activity_event_dispatch");
                break;
            case R.id.dispatch_iv:
                Log.d(TAG, "onClick: dispatch_iv");
                break;
            case R.id.dispatch_event_iv:
                Log.d(TAG, "onClick: dispatch_event_iv");
                break;
            case R.id.dispatch_ll:
                Log.d(TAG, "onClick: dispatch_ll");
                break;
            case R.id.dispatch_event_ll:
                Log.d(TAG, "onClick: dispatch_event_ll");
                break;
            case R.id.dispatch_event_btn:
                Log.d(TAG, "onClick: dispatch_event_btn");
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.activity_event_dispatch:
                Log.d(TAG, "onTouch: activity_event_dispatch");
                break;
            case R.id.dispatch_iv:
                Log.d(TAG, "onTouch: dispatch_iv");
                break;
            case R.id.dispatch_event_iv:
                Log.d(TAG, "onTouch: dispatch_event_iv");
                break;
            case R.id.dispatch_ll:
                Log.d(TAG, "onTouch: dispatch_ll");
                break;
            case R.id.dispatch_event_ll:
                Log.d(TAG, "onTouch: dispatch_event_ll");
                break;
            case R.id.dispatch_event_btn:
                Log.d(TAG, "onTouch: dispatch_event_btn");
                break;

        }
        return false;
    }
}
