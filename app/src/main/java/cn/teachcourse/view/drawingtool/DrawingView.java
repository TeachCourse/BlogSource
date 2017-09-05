package cn.teachcourse.view.drawingtool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public class DrawingView extends View {
    private ShapeDrawer shapeDrawer;//图形绘制器

    public DrawingView(Context context) {
        this(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //默认画线条
        shapeDrawer = new LineDrawer();
    }

    public void setShapeDrawer(ShapeDrawer shapeDrawer) {
        this.shapeDrawer = shapeDrawer;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ViewParams.areaWidth = this.getMeasuredWidth();
        ViewParams.areaHeight = this.getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (ViewParams.isRedo) {
            //撤消
            Bitmap bitmap=BitmapBuffer.getInstance().getBitmap();
            canvas.drawBitmap(bitmap, 0, 0, null);
            ViewParams.isRedo = false;
        } else {
            shapeDrawer.draw(canvas);
        }
        //逻辑
        shapeDrawer.logic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return shapeDrawer.onTouchEvent(event, this);
    }

}
