package cn.teachcourse.view.drawingtool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public class OvalDrawer extends ShapeDrawer{
    private float firstX;
    private float firstY;
    private float currentX;
    private float currentY;

    public OvalDrawer() {
        super();
    }

    @Override
    public void draw(Canvas viewCanvas) {
        super.draw(viewCanvas);
//        drawShape(viewCanvas, firstX, firstY, currentX, currentY);
    }

    /**
     * 画当前的形状
     *
     * @param canvas
     */
    protected void drawShape(Canvas canvas, float firstX,
                             float firstY, float currentX, float currentY) {
        Paint paint = PaintTool.getInstance().getPaint();
        if(firstX < currentX && firstY < currentY){
        //↘
            canvas.drawOval(new RectF(firstX, firstY, currentX, currentY), paint);
        }else if(firstX > currentX && firstY > currentY){
        //↖
            canvas.drawOval(new RectF(currentX, currentY, firstX, firstY), paint);
        }else if(firstX > currentX && firstY < currentY){
        //↙
            canvas.drawOval(new RectF(currentX, firstY, firstX, currentY), paint);
        }else if(firstX < currentX && firstY > currentY){
        //↗
            canvas.drawOval(new RectF(firstX, currentY, currentX, firstY), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event,View view) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = x;
                firstY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = x;
                currentY = y;
                view.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //将最终的矩形绘制在缓冲区
                Canvas canvas = BitmapBuffer.getInstance().getCanvas();
                drawShape(canvas, firstX, firstY, currentX, currentY);
                view.invalidate();
                //保存到撤消栈中
                BitmapBuffer.getInstance().pushBitmap();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void logic() {
    }
}
