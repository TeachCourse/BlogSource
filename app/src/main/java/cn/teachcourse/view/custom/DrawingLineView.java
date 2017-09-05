package cn.teachcourse.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public class DrawingLineView extends View {

    /**
     * 上一个点的坐标
     */
    private int preX, preY;
    /**
     * 当前点的坐标
     */
    private int currentX, currentY;
    /**
     * Bitmap 缓存区
     */
    private Bitmap bitmapBuffer;
    private Canvas bitmapCanvas;
    private Paint paint;

    public DrawingLineView(Context context) {
        this(context,null);
    }

    public DrawingLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
    }

    /**
     * 组件大小发生改变时回调 onSizeChanged 方法，我们在这里创建 Bitmap
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmapBuffer == null) {
            int width = getMeasuredWidth();//获取 View 的宽度
            int height = getMeasuredHeight(); //获取 View 的高度
            //新建 Bitmap 对象
            bitmapBuffer = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmapBuffer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将 Bitmap 中的内容绘制在 View 上
        canvas.drawBitmap(bitmapBuffer, 0, 0, null);
    }

    /**
     * 处理手势
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
        //手指按下，记录第一个点的坐标
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
        //手指移动，记录当前点的坐标
                currentX = x;
                currentY = y;
                bitmapCanvas.drawLine(preX, preY, currentX, currentY, paint);
                this.invalidate();
        //当前点的坐标成为下一个点的起始坐标
                preX = currentX;
                preY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }
}
