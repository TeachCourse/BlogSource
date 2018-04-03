package cn.teachcourse.view.imageview.large;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Scroller;


import cn.teahcourse.baseutil.DimensionUtil;

/**
 * Created by 钊林IT on 2018/1/31.
 */

public class FlingImageView extends View {
    private static final String TAG = "FlingImageView";
    private Point mPoint;
    private int preX, preY;

    private Scroller scroller;
    private Paint mPaint;
    private int slidingWidth;//可以滑动的距离
    private volatile Rect mRect = new Rect();//绘制的区域

    public FlingImageView(Context context) {
        super(context);
        init(context);
    }

    public FlingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlingImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(DimensionUtil.getUnitDip(context, 2));

        scroller = new Scroller(context);
        mPoint = getScreenSize(context);
        scroller = new Scroller(context);
        slidingWidth = (int) (mPoint.x * 0.1);

        mRect.left =0;
        mRect.top = 0;
        mRect.right = mRect.left + 100;
        mRect.bottom = mRect.top + 100;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1、获取控件宽、高的测量规格和大小
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i(TAG, "x:	" + x);
        Log.i(TAG, "y:	" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - preX;
                int dy = y - preY;

                this.scrollBy(-dx, -dy);//移动画布
                mRect.offset(dx, dy);//移动一次画布，重绘一次矩形
                preX = x;
                preY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                dx = x - preX;
                dy = y - preY;
                Log.i(TAG, "ACTION_UP dx:	" + dx);
                Log.i(TAG, "ACTION_UP dy:	" + dy);

                int remainX = slidingWidth - Math.abs(dx);
                int remainY = slidingWidth - Math.abs(dy);
                scroller.startScroll(getScrollX(), getScrollY(), -remainX, -remainY);
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            this.scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int x = getMeasuredWidth() / 2;
        int y = getMeasuredHeight() / 2;
        canvas.drawPoint(x, y, mPaint);
        canvas.drawRect(mRect,mPaint);
    }

    /**
     * 定义控件高度大小规则
     *
     * @param heightMeasureSpec
     * @return
     */

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        //如果高度规格为wrap_content，抛出异常
        if (mode == MeasureSpec.AT_MOST) {
            throw new IllegalStateException("layout_height can not be	wrap_content");
        }
        int height = 0;
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        }
        return height;
    }

    /**
     * 定义控件宽度大小规则
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        //如果高度规格为wrap_content，抛出异常
        if (mode == MeasureSpec.AT_MOST) {
            throw new IllegalStateException("layout_width can not be wrap_content");
        }
        int width = 0;
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        }
        return width * 3;
    }

    /*
     *	获取屏幕宽度
     *	@param	context
     *	@return
     */
    private Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point;
    }
}
