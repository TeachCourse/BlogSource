package cn.teachcourse.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.teahcourse.baseutil.DimensionUtil;

/**
 * Created by http://teachcourse.cn on 2018/4/17.
 */

public class DrawCircle extends View {
    private Paint mPaint;
    private Context mContext;
    private int mRadius = 50;

    public DrawCircle(Context context) {
        this(context, null);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int circleX = getWidth() / 2;
        int circleY = getHeight() / 2;


        canvas.drawCircle(circleX, circleY, circleX >= circleY ? circleY : circleX, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = customMeasure(widthMeasureSpec);
        int height = customMeasure(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int customMeasure(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;//需要被初始化
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            width = (int) DimensionUtil.getUnitDip(mContext, mRadius);
        }
        return width;
    }
}
