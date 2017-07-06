package cn.teachcourse.strategy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.teachcourse.UnitConvertUtil;
import cn.teachcourse.strategy.impl.DefaultClockImpl;

/**
 * Created by postmaster@teachcourse.cn on 2017/4/20.
 */

public class WatchViewImpl extends View{
    /**
     * 钟表半径，默认为0
     */
    private float radius;
    /**
     * 画笔
     */
    private Context context;
    private Paint paint;
    private IClock iClock;

    public void setIClock(IClock iClock) {
        this.iClock = iClock;
    }

    public WatchViewImpl(Context context) {
        this(context, null);
    }

    public WatchViewImpl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    /**
     * 初始化基本数据
     */
    private void init(Context context) {
        this.context=context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        iClock=new DefaultClockImpl(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resWidth;
        int resHeight;
        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            resWidth = resHeight = 0;
        } else {
            resWidth=width;
            resHeight=height;
        }
        setMeasuredDimension(resWidth, resHeight);
        /**
         * 测量的目的：获取当前矩形的宽度、高度，计算半径大小
         */
        if (radius == 0)
            radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2f;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint(canvas);
        postInvalidateDelayed(1000);
    }

    /**
     * 开始绘制钟表
     * @param canvas
     */
    public void paint(Canvas canvas) {
        iClock.paint(canvas);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getSpVal(float sp){
        return UnitConvertUtil.getSpVal(context,sp);
    }
    public float getDpVal(float dp){
        return UnitConvertUtil.getDpVal(context,dp);
    }
}
