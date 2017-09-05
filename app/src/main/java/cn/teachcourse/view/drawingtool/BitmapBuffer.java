package cn.teachcourse.view.drawingtool;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public class BitmapBuffer {
    private Bitmap bitmap;
    private Canvas canvas;
    private static BitmapBuffer self;

    private BitmapBuffer(int width, int height) {
        init(width, height);
    }

    private void init(int width, int height) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
    }

    public static BitmapBuffer getInstance() {
        if (self == null) {
            synchronized (BitmapBuffer.class) {
                if (self == null)
                    self = new BitmapBuffer(ViewParams.areaWidth, ViewParams.areaHeight);
            }
        }
        return self;
    }

    /**
     * 获取缓冲区的画布
     *
     * @return
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * 获得绘图结果
     *
     * @return
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 将当前的绘图结果保存到栈中
     */
    public void pushBitmap() {
        BitmapHistory.getInstance().pushBitmap(
                bitmap.copy(Bitmap.Config.ARGB_8888, false));
    }

    /**
     * 撤消
     */
    public void redo() {
        BitmapHistory his = BitmapHistory.getInstance();
        if (his.isReDo()) {
            Bitmap bmp = his.reDo();
            if (bmp != null) {
                bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
                //必须重新关联画布
                canvas.setBitmap(bitmap);
                //回收
                if (bmp != null && !bmp.isRecycled()) {
                    bmp.recycle();
                    System.gc();
                    bmp = null;
                }
            }
        }
    }
}
