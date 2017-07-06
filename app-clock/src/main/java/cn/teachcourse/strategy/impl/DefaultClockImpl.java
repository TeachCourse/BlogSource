package cn.teachcourse.strategy.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Calendar;

import cn.teachcourse.UnitConvertUtil;
import cn.teachcourse.strategy.IClock;
import cn.teachcourse.strategy.WatchViewImpl;

/**
 * Created by http://teachcourse.cn on 2017/7/5.
 */

public class DefaultClockImpl implements IClock {

    private WatchViewImpl watchView;
    /**
     * 钟表字体，默认大小
     */
    private static final int DEFAULT_TEXT_SIZE = 16;
    /**
     * 如果未设置内边距，使用该默认值
     */
    private static final float DEFAULT_PADDING = 1 / 25f;
    /**
     * 如果为设置时针形状大小，使用默认值
     */
    private static final float DEFAULT_HOUR_WIDTH = 5f;
    /**
     * 如果为设置分针形状大小，使用默认值
     */
    private static final float DEFAULT_MINUTE_WIDTH = 3f;
    /**
     * 如果为设置秒针形状大小，使用默认值
     */
    private static final float DEFAULT_SECOND_WIDTH = 2f;
    /**
     * 绘制时针、分针和秒针默认的圆角大小
     */
    private static final int DEFAULT_BORDER_RADIUS = 10;
    /**
     * 以当前View中心点位为准，时针、分针、秒针凸出的长度(偏移中心的距离)
     */
    private static final float DEFAULT_EXTRAS_WIDTH = 1 / 6f;
    /**
     * 设置默认属性变量
     */
    private float mPadding; //边距
    private float mTextSize; //文字大小
    private float mHourWidth; //时针宽度
    private float mMinuteWidth; //分针宽度
    private float mSecondWidth; //秒针宽度
    /**
     * 默认背景颜色
     */
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    /**
     * 默认外部背景颜色
     */
    private static final int DEFAULT_BACKGROUND_EXTERNAL_COLOR = Color.GRAY;
    /**
     * 默认时针/分针/秒针颜色
     */
    private static final int DEFAULT_HOUR_COLOR = Color.BLACK;
    private static final int DEFAULT_MINUTE_COLOR = Color.BLACK;
    private static final int DEFAULT_SECOND_COLOR = Color.RED;
    /**
     * 默认长/短刻度颜色
     */
    private static final int DEFAULT_LONG_SCALE_COLOR = Color.BLACK;
    private static final int DEFAULT_SHORT_SCALE_COLOR = Color.GRAY;
    /**
     * 设置默认属性变量
     */
    private int mHourColor; //时针的颜色
    private int mMinuteColor; //分针的颜色
    private int mSecondColor; //秒针的颜色
    private int mLongScaleColor; //长线的颜色
    private int mShortScaleColor; //短线的颜色
    private int mBackgroundColor;//钟表的背景色
    private int mBackgroundExternalColor;//钟表的背景色
    
    public DefaultClockImpl(WatchViewImpl watchView) {
        this.watchView = watchView;
    }
    
    @Override
    public void paint(Canvas canvas) {
        //移动画布中心点到当前View中心
        canvas.translate(getWidth() / 2, getHeight() / 2);
        //绘制外圆背景颜色
        paintExternalCircle(canvas);
        //绘制内圆背景颜色
        paintCircle(canvas);
        //绘制刻度
        paintScale(canvas);
        //绘制指针
        paintPointer(canvas);
    }



    /**
     * 绘制钟表外部圆形背景
     */
    public void paintExternalCircle(Canvas canvas) {
        getPaint().setColor(mBackgroundExternalColor == 0 ? DEFAULT_BACKGROUND_EXTERNAL_COLOR : mBackgroundExternalColor);
        getPaint().setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, getRadius(), getPaint());
    }

    /**
     * 绘制钟表的内部圆形背景
     *
     * @param canvas
     */
    public void paintCircle(Canvas canvas) {
        float padding = mPadding == 0 ? getRadius() * DEFAULT_PADDING : mPadding;
        getPaint().setColor(mBackgroundColor == 0 ? DEFAULT_BACKGROUND_COLOR : mBackgroundColor);
        getPaint().setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, getRadius() - padding, getPaint());
    }

    /**
     * 绘制钟表刻度
     *
     * @param canvas
     */
    private void paintScale(Canvas canvas) {
        float padding = mPadding == 0 ? getRadius() * DEFAULT_PADDING : mPadding;
        float textSize = mTextSize == 0 ? getSpVal(DEFAULT_TEXT_SIZE) : mTextSize;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制长刻度
                getPaint().setStrokeWidth(getDpVal(1.5f));
                getPaint().setColor(mLongScaleColor == 0 ? DEFAULT_LONG_SCALE_COLOR : mLongScaleColor);
                getPaint().setTextSize(textSize);
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();
                getPaint().getTextBounds(text, 0, text.length(), textBound);
                canvas.save();
                canvas.translate(0, -getRadius() + getDpVal(5) + 40 + padding + (textBound.bottom - textBound.top) / 2);
                getPaint().setStyle(Paint.Style.FILL);
                canvas.rotate(-6 * i);
                canvas.drawText(text, -(textBound.right + textBound.left) / 2, -(textBound.bottom + textBound.top) / 2, getPaint());
                canvas.restore();
            } else {
                //绘制短刻度
                getPaint().setStrokeWidth(getDpVal(1));
                getPaint().setColor(mShortScaleColor == 0 ? DEFAULT_SHORT_SCALE_COLOR : mShortScaleColor);
                getPaint().setStrokeWidth(getDpVal(1));
            }
            canvas.drawLine(0, -getRadius() + padding, 0, -getRadius() + padding + 30, getPaint());
            //旋转画布，实现圆弧绘制线条的效果
            canvas.rotate(6);
        }
    }

    /**
     * 绘制钟表上的时针、分针、秒针
     *
     * @param canvas
     */
    private void paintPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //时
        int minute = calendar.get(Calendar.MINUTE); //分
        int second = calendar.get(Calendar.SECOND); //秒
        int angleHour = (hour % 12) * 360 / 12; //时针转过的角度
        int angleMinute = minute * 360 / 60; //分针转过的角度
        int angleSecond = second * 360 / 60; //秒针转过的角度
        //绘制时针
        canvas.save();
        canvas.rotate(angleHour); //旋转到时针的角度
        float hourWidth = mHourWidth == 0 ? getDpVal(DEFAULT_HOUR_WIDTH) : mHourWidth;
        RectF rectFHour = new RectF(-hourWidth / 2, -getRadius() * 3 / 5, hourWidth / 2, getRadius() * DEFAULT_EXTRAS_WIDTH);
        getPaint().setColor(mHourColor == 0 ? DEFAULT_HOUR_COLOR : mHourColor); //设置指针颜色
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(hourWidth); //设置边界宽度
        canvas.drawRoundRect(rectFHour, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, getPaint()); //绘制时针
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(angleMinute);
        float minuteWidth = mMinuteWidth == 0 ? getDpVal(DEFAULT_MINUTE_WIDTH) : mMinuteWidth;
        RectF rectFMinute = new RectF(-minuteWidth / 2, -getRadius() * 3.5f / 5, minuteWidth / 2, getRadius() * DEFAULT_EXTRAS_WIDTH);
        getPaint().setColor(mMinuteColor == 0 ? DEFAULT_MINUTE_COLOR : mMinuteColor);
        getPaint().setStrokeWidth(minuteWidth);
        canvas.drawRoundRect(rectFMinute, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, getPaint());
        canvas.restore();
        //绘制秒针
        canvas.save();
        canvas.rotate(angleSecond);
        float secondWidth = mSecondWidth == 0 ? getDpVal(DEFAULT_SECOND_WIDTH) : mSecondWidth;
        RectF rectFSecond = new RectF(-secondWidth / 2, -getRadius() + 15, secondWidth / 2, getRadius() * DEFAULT_EXTRAS_WIDTH);
        getPaint().setColor(mSecondColor == 0 ? DEFAULT_SECOND_COLOR : mSecondColor);
        getPaint().setStrokeWidth(secondWidth);
        canvas.drawRoundRect(rectFSecond, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, getPaint());
        canvas.restore();
        //绘制中心小圆
        getPaint().setStyle(Paint.Style.FILL);
        getPaint().setColor(mSecondColor == 0 ? DEFAULT_SECOND_COLOR : mSecondColor);
        canvas.drawCircle(0, 0, secondWidth * 4, getPaint());
    }


    private float getRadius() {
        return watchView.getRadius();
    }

    private Paint getPaint() {
        return watchView.getPaint();
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
}
