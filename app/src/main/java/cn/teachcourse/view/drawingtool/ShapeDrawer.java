package cn.teachcourse.view.drawingtool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public abstract  class ShapeDrawer {
    public ShapeDrawer() {
        super();
    }
    /**
     * 用于绘图
     * @param viewCanvas
     * 用于展示结果的画布
     * @return
     */
    public void draw(Canvas viewCanvas){
    //画历史结果
        Bitmap bitmap = BitmapBuffer.getInstance().getBitmap();
        viewCanvas.drawBitmap(bitmap, 0, 0, null);
    }
    /**
     * 用于响应触摸事件
     *
     * @param event
     * @return
     */
    public abstract boolean onTouchEvent(MotionEvent event,View view);
    /**
     * 绘图的逻辑
     */
    public abstract void logic();
}
