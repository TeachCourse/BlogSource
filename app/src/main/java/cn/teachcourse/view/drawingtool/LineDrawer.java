package cn.teachcourse.view.drawingtool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public class LineDrawer extends ShapeDrawer {
    /**
     * 上一个点的坐标
     */
    private int preX, preY;
    /**
     * 当前点的坐标
     */
    private int currentX, currentY;

    public LineDrawer() {
        super();
    }

    @Override
    public void draw(Canvas viewCanvas) {
        super.draw(viewCanvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event,View view) {
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

                break;
            case MotionEvent.ACTION_UP:
                Canvas canvas = BitmapBuffer.getInstance().getCanvas();
                drawLine(canvas,preX,preY,currentX,currentY);
                view.invalidate();
                //当前点的坐标成为下一个点的起始坐标
                preX = currentX;
                preY = currentY;
                //保存到撤消栈中
                BitmapBuffer.getInstance().pushBitmap();
                break;
            default:
                break;
        }
        return true;
    }

    private void drawLine(Canvas canvas,float startX,float startY,float endX,float endY) {
        Paint paint = PaintTool.getInstance().getPaint();
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    @Override
    public void logic() {

    }
}
