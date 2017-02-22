package cn.teachcourse.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by postmaster@teachcourse.cn on 2016/12/19.
 */

public class MathUtilActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
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

}
