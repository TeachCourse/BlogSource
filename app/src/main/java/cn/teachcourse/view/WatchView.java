package cn.teachcourse.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * Created by postmaster@teachcourse.cn on 2017/4/20.
 */

public class WatchView extends View {
    /**
     * 钟表半径，默认为0
     */
    private float mRadius;
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
    private static final float DEFAULT_HOUR_WIDTH = 5;
    /**
     * 如果为设置分针形状大小，使用默认值
     */
    private static final float DEFAULT_MINUTE_WIDTH = 3;
    /**
     * 如果为设置秒针形状大小，使用默认值
     */
    private static final float DEFAULT_SECOND_WIDTH = 2;
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
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;    /**
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
    /**
     * 画笔
     */
    private Paint mPaint;

    public WatchView(Context context) {
        this(context, null);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化基本数据
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    public float getDpVal(float dp) {
        Resources res = getContext().getResources();
        if (res != null)
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
        return dp;
    }

    public float getSpVal(float sp) {
        Resources res = getContext().getResources();
        if (res != null)
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
        return sp;
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
        if (mRadius == 0)
            mRadius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2f;
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
    private void paint(Canvas canvas) {
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
        mPaint.setColor(mBackgroundExternalColor == 0 ? DEFAULT_BACKGROUND_EXTERNAL_COLOR : mBackgroundExternalColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mRadius, mPaint);
    }
    /**
     * 绘制钟表的内部圆形背景
     *
     * @param canvas
     */
    public void paintCircle(Canvas canvas) {
        float padding = mPadding == 0 ? mRadius*DEFAULT_PADDING : mPadding;
        mPaint.setColor(mBackgroundColor == 0 ? DEFAULT_BACKGROUND_COLOR : mBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mRadius-padding, mPaint);
    }

    /**
     * 绘制钟表刻度
     *
     * @param canvas
     */
    private void paintScale(Canvas canvas) {
        float padding = mPadding == 0 ? mRadius*DEFAULT_PADDING : mPadding;
        float textSize = mTextSize == 0 ? getSpVal(DEFAULT_TEXT_SIZE) : mTextSize;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制长刻度
                mPaint.setStrokeWidth(getDpVal(1.5f));
                mPaint.setColor(mLongScaleColor == 0 ? DEFAULT_LONG_SCALE_COLOR : mLongScaleColor);
                mPaint.setTextSize(textSize);
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                canvas.save();
                canvas.translate(0, -mRadius + getDpVal(5) + 40 + padding + (textBound.bottom - textBound.top) / 2);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.rotate(-6 * i);
                canvas.drawText(text, -(textBound.right + textBound.left) / 2, -(textBound.bottom + textBound.top) / 2, mPaint);
                canvas.restore();
            } else {
                //绘制短刻度
                mPaint.setStrokeWidth(getDpVal(1));
                mPaint.setColor(mShortScaleColor == 0 ? DEFAULT_SHORT_SCALE_COLOR : mShortScaleColor);
                mPaint.setStrokeWidth(getDpVal(1));
            }
            canvas.drawLine(0, -mRadius + padding, 0, -mRadius + padding + 30, mPaint);
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
        RectF rectFHour = new RectF(-hourWidth / 2, -mRadius * 3 / 5, hourWidth / 2, mRadius * DEFAULT_EXTRAS_WIDTH);
        mPaint.setColor(mHourColor == 0 ? DEFAULT_HOUR_COLOR : mHourColor); //设置指针颜色
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(hourWidth); //设置边界宽度
        canvas.drawRoundRect(rectFHour, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, mPaint); //绘制时针
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(angleMinute);
        float minuteWidth = mMinuteWidth == 0 ? getDpVal(DEFAULT_MINUTE_WIDTH) : mMinuteWidth;
        RectF rectFMinute = new RectF(-minuteWidth / 2, -mRadius * 3.5f / 5, minuteWidth / 2, mRadius * DEFAULT_EXTRAS_WIDTH);
        mPaint.setColor(mMinuteColor == 0 ? DEFAULT_MINUTE_COLOR : mMinuteColor);
        mPaint.setStrokeWidth(minuteWidth);
        canvas.drawRoundRect(rectFMinute, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, mPaint);
        canvas.restore();
        //绘制秒针
        canvas.save();
        canvas.rotate(angleSecond);
        float secondWidth = mSecondWidth == 0 ? getDpVal(DEFAULT_SECOND_WIDTH) : mSecondWidth;
        RectF rectFSecond = new RectF(-secondWidth / 2, -mRadius + 15, secondWidth / 2, mRadius * DEFAULT_EXTRAS_WIDTH);
        mPaint.setColor(mSecondColor == 0 ? DEFAULT_SECOND_COLOR : mSecondColor);
        mPaint.setStrokeWidth(secondWidth);
        canvas.drawRoundRect(rectFSecond, DEFAULT_BORDER_RADIUS, DEFAULT_BORDER_RADIUS, mPaint);
        canvas.restore();
        //绘制中心小圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mSecondColor == 0 ? DEFAULT_SECOND_COLOR : mSecondColor);
        canvas.drawCircle(0, 0, secondWidth * 4, mPaint);
    }

    public static class Builder {
        private Context context;
        /**
         * 定义相关默认属性
         */
        private float radius;//设置半径长度
        private float padding; //边距

        public void setRadius(float radius) {
            this.radius = radius;
        }

        private float textSize; //文字大小
        private float hourWidth; //时针宽度
        private float minuteWidth; //分针宽度
        private float secondWidth; //秒针宽度
        /**
         * 默认颜色
         */
        private int longScaleColor; //长线的颜色
        private int shortScaleColor; //短线的颜色
        private int hourColor; //时针的颜色
        private int minuteColor; //分针的颜色
        private int secondColor; //秒针的颜色
        private int backgroundColor;//钟表的背景色
        private int backgroundExternalColor;//钟表的背景色

        public void setBackgroundExterColor(int backgroundExterColor) {
            this.backgroundExternalColor = backgroundExterColor;
        }

        public void setTextSize(float textSize) {
            this.textSize = textSize;
        }

        public void setHourWidth(float hourWidth) {
            this.hourWidth = hourWidth;
        }

        public void setMinuteWidth(float minuteWidth) {
            this.minuteWidth = minuteWidth;
        }

        public void setSecondWidth(float secondWidth) {
            this.secondWidth = secondWidth;
        }

        public void setLongScaleColor(int longScaleColor) {
            this.longScaleColor = longScaleColor;
        }

        public void setShortScaleColor(int shortScaleColor) {
            this.shortScaleColor = shortScaleColor;
        }

        public void setHourColor(int hourColor) {
            this.hourColor = hourColor;
        }

        public void setMinuteColor(int minuteColor) {
            this.minuteColor = minuteColor;
        }

        public void setSecondColor(int secondColor) {
            this.secondColor = secondColor;
        }

        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setPadding(float padding) {
            this.padding = padding;
            return this;
        }


        public WatchView create() {
            final WatchView watchView = new WatchView(context);
            /**
             * 设置大小
             */
            watchView.mRadius = radius;
            watchView.mPadding = padding;
            watchView.mTextSize = textSize;
            watchView.mHourWidth = hourWidth;
            watchView.mMinuteWidth = minuteWidth;
            watchView.mSecondWidth = secondWidth;
            /**
             * 设置颜色值
             */
            watchView.mHourColor = hourColor;
            watchView.mMinuteColor = minuteColor;
            watchView.mSecondColor = secondColor;
            watchView.mLongScaleColor = longScaleColor;
            watchView.mShortScaleColor = shortScaleColor;
            watchView.mBackgroundColor = backgroundColor;
            watchView.mBackgroundExternalColor = backgroundExternalColor;
            return watchView;
        }
    }
}
