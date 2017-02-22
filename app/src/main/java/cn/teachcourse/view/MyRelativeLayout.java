package cn.teachcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by postmaster@teachcourse.cn on 2016/11/17.
 */

public class MyRelativeLayout extends RelativeLayout {
    private static final String TAG = "MyRelativeLayout";

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = super.onInterceptTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag=false;
                Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN " + flag);
                break;
            case MotionEvent.ACTION_MOVE:
                flag=true;
                Log.d(TAG, "onInterceptTouchEvent: ACTION_MOVE " + flag);
                break;
            case MotionEvent.ACTION_UP:
                flag=false;
                Log.d(TAG, "onInterceptTouchEvent: ACTION_UP " + flag);
                break;
        }
        return flag;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean flag = super.dispatchTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "dispatchTouchEvent: ACTION_DOWN " + flag);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "dispatchTouchEvent: ACTION_MOVE " + flag);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "dispatchTouchEvent: ACTION_UP " + flag);
                break;
        }
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 返回true，表示当前view消耗事件
         */
        boolean flag = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN " + flag);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE " + flag);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP " + flag);
                break;
        }
        return flag;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        Log.d(TAG, "requestDisallowInterceptTouchEvent: "+disallowIntercept);
    }
}
