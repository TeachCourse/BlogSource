package cn.teachcourse.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by postmaster@teachcourse.cn on 2016/11/16.
 */

public class MyViewPager extends ViewPager {
    private static final String TAG = "MyViewPager";
    private double mLastX;
    private double mLastY;
    private ViewGroup mParentView;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmParentView(ViewGroup mParentView) {
        this.mParentView = mParentView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean flag = super.dispatchTouchEvent(event);
        double x = (int) event.getX();
        double y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (mParentView != null)
                    mParentView.requestDisallowInterceptTouchEvent(false);
                if (mParentView.getParent() != null)
                    mParentView.getParent().requestDisallowInterceptTouchEvent(false);
                Log.d(TAG, "dispatchTouchEvent: ACTION_DOWN \nX="+x+"\n"+"Y="+y);
                break;
            case MotionEvent.ACTION_MOVE:
                double deltaX = x - mLastX;
                double deltaY = y - mLastY;
                double degrees=calculateDegrees(deltaX,deltaY);
                if (isSuitable(degrees)){
//                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    if (mParentView != null)
                        mParentView.requestDisallowInterceptTouchEvent(true);
                    if (mParentView.getParent() != null)
                        mParentView.getParent().requestDisallowInterceptTouchEvent(true);
                }
                Log.d(TAG, "dispatchTouchEvent: ACTION_MOVE \ndeltaX="+deltaX+"\n"+"deltaY="+deltaY+"\n角度="+degrees);
                break;
            case MotionEvent.ACTION_UP:
                if (mParentView != null)
                    mParentView.requestDisallowInterceptTouchEvent(false);
                if (mParentView.getParent() != null)
                    mParentView.getParent().requestDisallowInterceptTouchEvent(false);
                Log.d(TAG, "dispatchTouchEvent: ACTION_UP \nX="+x+"\n"+"Y="+y);
                break;
            default:
                break;
        }

        return flag;

    }

    /**
     * 判断是否角度
     * @return
     */
    private boolean isSuitable(double degrees) {
        if (degrees>45&&degrees<135|degrees>225&&degrees<315)
            return false;

        return true;

    }

    /**
     * 根据反正切计算弧度，再由弧度计算角度
     * @param deltaX
     * @param deltaY
     * @return
     */
    private double calculateDegrees(double deltaX, double deltaY) {
        deltaX=Math.abs(deltaX);
        deltaY=Math.abs(deltaY);
        double degrees=Math.toDegrees(Math.toRadians(deltaX/deltaY));
        return degrees;
    }


}
