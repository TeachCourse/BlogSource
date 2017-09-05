package cn.teachcourse.view.drawingtool;

import java.io.Serializable;

/**
 * Created by http://teachcourse.cn on 2017/6/1.
 */

public class PointModel implements Serializable{
    private float startx;
    private float starty;
    private float endx;
    private float endy;
    public static final int TPEY_LINE=0;
    public static final int TPEY_PATH=1;
    public static final int TPEY_CIRCLE=2;
    public static final int TPEY_RECT=3;
    private int type;//绘制类型 0直线 1轨迹 2圆 3矩形

    public PointModel(float startx, float starty, float endx, float endy, int type) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getStartx() {
        return startx;
    }

    public void setStartx(float startx) {
        this.startx = startx;
    }

    public float getStarty() {
        return starty;
    }

    public void setStarty(float starty) {
        this.starty = starty;
    }

    public float getEndx() {
        return endx;
    }

    public void setEndx(float endx) {
        this.endx = endx;
    }

    public float getEndy() {
        return endy;
    }

    public void setEndy(float endy) {
        this.endy = endy;
    }
}
