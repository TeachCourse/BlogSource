package cn.teachcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by postmaster@teachcourse.cn on 2016/11/17.
 */

public class MyImageView extends ImageView {
    private static final String TAG = "MyImageView";

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        boolean flag = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mParentView != null) {
                    mParentView.requestDisallowInterceptTouchEvent(true);
                    Log.d(TAG, "requestDisallowInterceptTouchEvent: "+true);
                }
                Log.d(TAG, "onTouchEvent: ACTION_DOWN " + flag);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mParentView != null){
                    mParentView.requestDisallowInterceptTouchEvent(true);
                    Log.d(TAG, "requestDisallowInterceptTouchEvent: "+true);
                }
                Log.d(TAG, "onTouchEvent: ACTION_MOVE " + flag);
                break;
            case MotionEvent.ACTION_UP:
                if (mParentView != null) {
                    mParentView.requestDisallowInterceptTouchEvent(true);
                    Log.d(TAG, "requestDisallowInterceptTouchEvent: "+true);
                }
                Log.d(TAG, "onTouchEvent: ACTION_UP " + flag);
                break;
        }
        return flag;
    }

    private ViewGroup mParentView;

    public void setmParentView(ViewGroup mParentView) {
        this.mParentView = mParentView;
    }
}
