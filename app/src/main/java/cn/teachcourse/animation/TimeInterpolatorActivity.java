package cn.teachcourse.animation;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import cn.teachcourse.R;
import cn.teachcourse.view.custom.DrawCircle;
import cn.teachcourse.view.custom.ViewWrapper;

public class TimeInterpolatorActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewWrapper mDrawCircleWrapper;
    private int mWidth = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_interpolator);
        final DrawCircle drawCircle = (DrawCircle) findViewById(R.id.draw_circle_view);
        mDrawCircleWrapper = new ViewWrapper(drawCircle);


        findViewById(R.id.accelerate_decelerate_btn).setOnClickListener(this);
        findViewById(R.id.accelerate_btn).setOnClickListener(this);
        findViewById(R.id.anticipate_btn).setOnClickListener(this);
        findViewById(R.id.anticipate_over_shoot_btn).setOnClickListener(this);
        findViewById(R.id.bounce_btn).setOnClickListener(this);
        findViewById(R.id.cycle_btn).setOnClickListener(this);
        findViewById(R.id.decelerate_btn).setOnClickListener(this);
        findViewById(R.id.linear_btn).setOnClickListener(this);
        findViewById(R.id.over_shoot_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.accelerate_decelerate_btn:

                setTimeInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case R.id.accelerate_btn:

                setTimeInterpolator(new AccelerateInterpolator());
                break;
            case R.id.anticipate_btn:

                setTimeInterpolator(new AnticipateInterpolator());
                break;
            case R.id.anticipate_over_shoot_btn:

                setTimeInterpolator(new AnticipateOvershootInterpolator());
                break;
            case R.id.bounce_btn:

                setTimeInterpolator(new BounceInterpolator());
                break;
            case R.id.cycle_btn:

                setTimeInterpolator(new CycleInterpolator(10));
                break;
            case R.id.decelerate_btn:

                setTimeInterpolator(new DecelerateInterpolator());
                break;
            case R.id.linear_btn:

                setTimeInterpolator(new LinearInterpolator());
                break;
            case R.id.over_shoot_btn:

                setTimeInterpolator(new OvershootInterpolator());
                break;
        }
    }

    private void setTimeInterpolator(TimeInterpolator interpolator) {
        ValueAnimator anim = ObjectAnimator.ofFloat(mDrawCircleWrapper, "x", mWidth);
        anim.setDuration(2000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setInterpolator(interpolator);
        anim.start();
    }
}
