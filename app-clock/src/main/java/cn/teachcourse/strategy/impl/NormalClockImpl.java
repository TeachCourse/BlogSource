package cn.teachcourse.strategy.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Calendar;

import cn.teachcourse.strategy.IClock;
import cn.teachcourse.strategy.WatchViewImpl;

/**
 * Created by http://teachcourse.cn on 2017/7/5.
 */

public class NormalClockImpl implements IClock {
    private WatchViewImpl watchView;
    private Calendar calendar;
    
    public NormalClockImpl(WatchViewImpl watchView) {
        this.watchView = watchView;
        calendar = Calendar.getInstance();
    }

    @Override
    public void paint(Canvas canvas) {
        //计算圆盘直径，取短的
        int len = Math.min(getWidth(), getHeight());
        //绘制表盘
        drawPlate(canvas, len);
        //绘制指针
        drawPoints(canvas, len);
    }

    /**
     * 绘制表盘
     *
     * @param canvas
     * @param len    组件宽度
     */
    protected void drawPlate(Canvas canvas, int len) {
        canvas.save();
        //画圆
        int r = len / 2;
        canvas.drawCircle(r, r, r, getPaint());
        //画刻度(一共有 60 根)
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //长刻度,长刻度占圆半径的 1/10
                getPaint().setColor(Color.RED);
                getPaint().setStrokeWidth(4);
                canvas.drawLine(r + 9 * r / 10, r, len, r, getPaint());
            } else {
                //短刻度,长刻度占圆半径的 1/15
                getPaint().setColor(Color.GRAY);
                getPaint().setStrokeWidth(1);
                canvas.drawLine(r + 14 * r / 15, r, len, r, getPaint());
            }
            //以（r，r）为中心，将画布旋转 6 度
            canvas.rotate(6, r, r);
        }
        canvas.restore();
    }

    /**
     * 画指针
     *
     * @param canvas
     * @param len
     */
    protected void drawPoints(Canvas canvas, int len) {
        //先获取系统时间
        calendar.setTimeInMillis(System.currentTimeMillis());
        //获取时分秒
        int hours = calendar.get(Calendar.HOUR) % 12;//转换为 12 小时制
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        //画时针
        //角度（顺时针）
        int degree = 360 / 12 * hours;
        //转换成弧度
        double radians = Math.toRadians(degree);
        //根据当前时计算时针两个点的坐标
        //起点（圆中心），终点：计算得到
        int r = len / 2;
        int startX = r;
        int startY = r;
        int endX = (int) (startX + r * 0.5 * Math.cos(radians));
        int endY = (int) (startY + r * 0.5 * Math.sin(radians));
        canvas.save();
        getPaint().setStrokeWidth(4);
        //0 度从 3 点处开始，时间从 12 点处开始，所以需要将画布旋转 90 度
        canvas.rotate(-90, r, r);
        canvas.drawLine(startX, startY, endX, endY, getPaint());
        canvas.restore();

        //画分针
        //计算角度
        degree = 360 / 60 * minutes;
        radians = Math.toRadians(degree);
        endX = (int) (startX + r * 0.6 * Math.cos(radians));
        endY = (int) (startY + r * 0.6 * Math.sin(radians));
        canvas.save();
        getPaint().setStrokeWidth(2);
        //0 度从 3 点处开始，时间从 12 点处开始，所以需要将画布旋转 90 度
        canvas.rotate(-90, r, r);
        canvas.drawLine(startX, startY, endX, endY, getPaint());
        canvas.restore();

        //画秒针
        degree = 360 / 60 * seconds;
        radians = Math.toRadians(degree);
        endX = (int) (startX + r * 0.8 * Math.cos(radians));
        endY = (int) (startY + r * 0.8 * Math.sin(radians));
        canvas.save();
        getPaint().setStrokeWidth(1);
        //0 度从 3 点处开始，时间从 12 点处开始，所以需要将画布旋转 90 度
        canvas.rotate(-90, r, r);
        canvas.drawLine(startX, startY, endX, endY, getPaint());

        //再给秒针画个“尾巴”
        radians = Math.toRadians(degree - 180);
        endX = (int) (startX + r * 0.15 * Math.cos(radians));
        endY = (int) (startY + r * 0.15 * Math.sin(radians));
        canvas.drawLine(startX, startY, endX, endY, getPaint());
        canvas.restore();
    }

    private float getRadius() {
        return watchView.getRadius();
    }

    private Paint getPaint() {
        Paint paint=watchView.getPaint();
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private float getSpVal(float sp) {
        return watchView.getSpVal(sp);
    }

    private float getDpVal(float dp) {
        return watchView.getDpVal(dp);
    }

    private int getHeight() {
        return watchView.getHeight();
    }

    private int getWidth() {
        return watchView.getWidth();
    }
}
