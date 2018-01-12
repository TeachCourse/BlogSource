package cn.teachcourse.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.teachcourse.common.BaseActivity;

/**
 * Created by postmaster@teachcourse.cn on 2016/12/19.
 */

public class MathUtilActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        TextView tv=new TextView(this);
        tv.setText(calculateDegrees(1.0,1.0)+"");
        setContentView(tv);
    }

    /**
     * 根据反正切计算弧度，再由弧度计算角度
     *
     * @param deltaX
     * @param deltaY
     * @return
     */
    private double calculateDegrees(double deltaX, double deltaY) {
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);
        double degree=Math.atan(deltaY/deltaX);
        return Math.toDegrees(degree);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
