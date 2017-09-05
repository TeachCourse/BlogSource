package cn.teachcourse.view.drawingtool;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by http://teachcourse.cn on 2017/5/31.
 */

public class PaintTool {
    /** 绘图颜色 */
    private int color;
    /**
     * 线条的宽度
     */
    private int borderWidth;
    /**
     * 是否填充，默认是空心
     */
    private boolean fill;
    private static PaintTool self;
    private static Paint paint;

    /**
     * 将构造方法定义成为私有，目的为了防止创建对象
     */
    private PaintTool() {
        reset();
    }

    /**
     * 向外部提供对象
     *
     * @return
     */
    public static PaintTool getInstance() {
        if (self == null) {
            synchronized (PaintTool.class) {
                if (self == null)
                    self = new PaintTool();
            }

        }
        return self;
    }

    /**
     * 将当前的绘图属性转换成 Paint 对象
     *
     * @return
     */
    public Paint getPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setAntiAlias(true);
        paint.setColor(this.color);
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(this.fill ? Paint.Style.FILL : Paint.Style.STROKE);
        paint.setTextSize(30);
        return paint;
    }

    /**
     * 重置
     */
    public void reset() {
        this.color = Color.BLACK;
        this.borderWidth = 1;
        this.fill = false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
