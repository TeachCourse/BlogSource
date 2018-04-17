package cn.teachcourse.view.custom;

import android.view.View;

/**
 * Created by http://teachcourse.cn on 2018/4/17.
 */

public class ViewWrapper {
    private View view;
    private float x = 0, y = 0;

    public ViewWrapper(View view) {
        this.view = view;
    }

    public int getWidth() {
        return view.getLayoutParams().width;
    }

    public void setWidth(int width) {
        view.getLayoutParams().width = width;
        view.requestLayout();
    }

    public void setX(float value) {
        view.setX(value);
    }

    public float getX() {
        return view.getX();
    }

    public void setY(float value) {
        view.setY(value);
    }

    public float getY() {
        return view.getY();
    }
}
