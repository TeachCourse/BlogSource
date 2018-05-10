package cn.teachcourse.animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import cn.teachcourse.R;
import cn.teachcourse.view.custom.DrawCircle;
import cn.teachcourse.view.custom.ViewWrapper;
import cn.teahcourse.baseutil.DimensionUtil;

public class TypeEvaluatorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TypeEvaluatorActivity";
    private ViewWrapper mViewWrapper;
    private int mLayoutWidth = 500;
    private int mLayoutHeight = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_evaluator);
        final DrawCircle drawCircle = new DrawCircle(this);
        mViewWrapper = new ViewWrapper(drawCircle);

        findViewById(R.id.parabola_btn).setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parabola_btn:

                customEvaluator(new OscillationEvaluator(mLayoutHeight));
                break;
        }
    }

    /**
     * 小球动画
     */
    private void customEvaluator(TypeEvaluator evaluator) {
        float radius = DimensionUtil.getUnitDip(this, 50);
        Point startPoint = new Point(radius, radius);
        Point endPoint = new Point(mLayoutWidth - radius, radius);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startPoint, endPoint);
        animator.setDuration(2000).setRepeatCount(1);
        animator.setInterpolator(new LinearInterpolator());//设置插值器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point currentPoint = (Point) animation.getAnimatedValue();
                DrawCircle drawCircle = (DrawCircle) mViewWrapper.getView();
                drawCircle.setPointF(new PointF(currentPoint.getX(), currentPoint.getY()));
                drawCircle.invalidate();
                Log.d(TAG, "-------> X=" + currentPoint.getX()+" ,Y="+currentPoint.getY());
            }
        });
        animator.start();
    }

    static class Point {

        private float x;
        private float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    /**
     *
     */
    static class OscillationEvaluator implements TypeEvaluator {
        private int height;

        public OscillationEvaluator(int height) {
            this.height = height;
        }

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());//x坐标线性变化
            float y = 120 * (float) (Math.sin(0.01 * Math.PI * x)) + height / 2;//y坐标取相对应函数值
            return new Point(x, y);
        }
    }
}
