package cn.teachcourse.strategy.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.TimeZone;

import cn.teachcourse.strategy.IClock;
import cn.teachcourse.strategy.WatchViewImpl;

/**
 * Created by http://teachcourse.cn on 2017/7/5.
 */

public class DesignClockImpl implements IClock {
    private WatchViewImpl watchView;
    private static final int H_POINTER_WIDTH = 15; //时针宽
    private static final int M_POINTER_WIDTH = 10; //分针宽
    private static final int S_POINTER_WIDTH = 5; //秒针宽
    private static final int SCALE_LINE_LENGTH = 50; //刻度线长
    private static final int SCALE_LINE_WIDTH = 6; //刻度线宽
    private static final int M_S_DEGREES_UNIT = 360 / 60; //分、秒针每个数字走的角度
    private static final int H_DEGREES_UNIT = 360 / 12; //时针每个数字走的角度
    private Paint mPaint;
    private int mW, mH, mCx, mCy, mR;
    private int mCount;
    private Path mPath;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postInvalidate();
        }

    };


    public DesignClockImpl(WatchViewImpl watchView) {
        this.watchView = watchView;
        mPaint=new Paint();
        mPath = new Path();
    }

    @Override
    public void paint(Canvas canvas) {
        long beginTime = SystemClock.uptimeMillis();
        mW = getWidth();
        mH = getHeight();

        mR = Math.min(mW, mH) / 2 - 20;
        mCx = mW / 2;
        mCy = mH / 2;

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCx, mCy, mR, mPaint); //外圆
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mCx, mCy, mR / 12, mPaint); //圆心

        /*
        绘制刻度
         */
        for (int i = 0; i < 60; i++) {
            canvas.save();
            canvas.rotate(M_S_DEGREES_UNIT * i, mCx, mCy);
            mPaint.setStrokeWidth(SCALE_LINE_WIDTH);
            canvas.drawLine(mCx, mCy - mR, mCx, mCy - mR + SCALE_LINE_LENGTH, mPaint);
            if (i % 5 == 0) {//小时刻度和数字
                mPaint.setStrokeWidth(SCALE_LINE_WIDTH + 5);
                canvas.drawLine(mCx, mCy - mR, mCx, mCy - mR + SCALE_LINE_LENGTH + 10, mPaint);

                String num = i == 0 ? 12 + "" : i / 5 + "";
                float x = mCx - mPaint.measureText(num) / 2;
                float y = mCy - mR + SCALE_LINE_LENGTH + 30;
                mPaint.setTextSize(50);
                canvas.drawText(num, x, y + Math.abs(mPaint.ascent()), mPaint);
            }
            canvas.restore();

        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        /*
        分针在最下面 最先绘制
         */
        canvas.save();
        float mDegrees = minute * M_S_DEGREES_UNIT;
        canvas.rotate(mDegrees, mCx, mCy);
        mPaint.setColor(Color.GREEN);
        mPath.reset();
        mPath.addRect(new RectF(mCx - M_POINTER_WIDTH, mCy - mR / 4 * 3, mCx + M_POINTER_WIDTH, mCy + mR / 5), Path.Direction.CW);
        mPath.quadTo(mCx, mCy - mR / 4 * 3 - 40, mCx + M_POINTER_WIDTH, mCy - mR / 4 * 3);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        /*
        时针在中间
            分针60分走360度; 时针1小时(即60分)走30度
            mDegrees:hDegrees = 360:30 = 12:1
            hDegrees = mDegrees / 12; 时针相对于分钟数所要走的角度
         */
        canvas.save();
        canvas.rotate(mDegrees / 12 + hour * H_DEGREES_UNIT, mCx, mCy);
        mPaint.setColor(Color.RED);
        mPath.reset();
        mPath.addRect(new RectF(mCx - H_POINTER_WIDTH, mCy - mR / 3 * 2, mCx + H_POINTER_WIDTH, mCy + mR / 5), Path.Direction.CW);
        mPath.quadTo(mCx, mCy - mR / 3 * 2 - 30, mCx + H_POINTER_WIDTH, mCy - mR / 3 * 2);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        /*
        秒针在最上面
         */
        canvas.save();
        canvas.rotate(second * M_S_DEGREES_UNIT, mCx, mCy);
        mPaint.setColor(Color.BLACK);
        mPath.reset();
        mPath.addRect(new RectF(mCx - S_POINTER_WIDTH, mCy - mR / 5 * 4, mCx + S_POINTER_WIDTH, mCy + mR / 5), Path.Direction.CW);
        mPath.quadTo(mCx, mCy - mR / 5 * 4 - 30, mCx + S_POINTER_WIDTH, mCy - mR / 5 * 4);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        long spanTime = SystemClock.uptimeMillis() - beginTime;
//        System.out.println("间隔时间" + spanTime);
        if (mCount < 5) {//12的位置开始绘制时位置不对，刷新一次后就正常，这里让其快速刷新几次
            mHandler.sendEmptyMessage(0);
            mCount++;
        } else
            mHandler.sendEmptyMessageDelayed(0, 1000 - spanTime);
    }

    private float getRadius() {
        return watchView.getRadius();
    }

    private Paint getPaint() {
        Paint paint = watchView.getPaint();
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private float getSpVal(float sp) {
        return watchView.getSpVal(sp);
    }

    private float getDpVal(float dp) {
        return watchView.getDpVal(dp);
    }

    private int getHeight() {
        return watchView.getHeight();
    }

    private int getWidth() {
        return watchView.getWidth();
    }

    private void postInvalidate() {
        watchView.postInvalidate();
    }
}
