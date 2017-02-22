package cn.teachcourse.view.layoutparams;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class LinearLayoutParamsActivity extends BaseActivity {
    private RelativeLayout mParentView_rl;
    private LinearLayout mParentView_ll;
    private TextView mMultipleProperties_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_params);
        initView();
//        addViews();
//        setLayoutParams();
        setMarginLayoutParams();
//        getAttributeSet();
    }

    private void getAttributeSet() {
        XmlPullParser parser=getResources().getXml(R.xml.activity_address);
        AttributeSet attributes= Xml.asAttributeSet(parser);
        int count=attributes.getAttributeCount();

    }

    private void setMarginLayoutParams() {
        float density = getResources().getDisplayMetrics().density;
        int width=getResources().getDisplayMetrics().widthPixels;
        int pixels = (int) (16 * density);
        ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(width, width);
        params.setMargins(pixels,pixels,pixels,pixels);
        TextView tv = new TextView(this);
        tv.setText("动态设置RelativeLayout.LayoutParams：margin：16dp");
        tv.setLayoutParams(params);
        mParentView_ll.addView(tv);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAnimate(v,100,500);
            }
        });
    }

    private void setLayoutParams() {
        /**
         * 子控件的width：wrap_content，height:wrap_content
         * 与父控件底部对齐，margin：16dp
         */
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, R.id.activity_linear_layout_params_rl);
        float density = getResources().getDisplayMetrics().density;
        int pixels = (int) (16 * density);
        params.setMargins(pixels, pixels, pixels, pixels);
        TextView tv = new TextView(this);
        tv.setText("动态设置RelativeLayout.LayoutParams：margin：16dp");
        tv.setLayoutParams(params);
        mParentView_rl.addView(tv);

    }

    private void addViews() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        params.gravity = Gravity.RIGHT;
        float density = getResources().getDisplayMetrics().density;
        float densityDpi = getResources().getDisplayMetrics().densityDpi;
        params.setMargins(0, 0, (int) (16 * density), 0);

        TextView tv = null;
        int i = 0;
        while (i < 3) {
            tv = new TextView(this);
//            tv.setLayoutParams(mMultipleProperties_tv.getLayoutParams());
            tv.setLayoutParams(params);
            tv.setText("复制属性：LayoutParams " + i);
            mParentView_ll.addView(tv);
            i++;
        }
    }

    private void initView() {
        initCommon(getWindow().getDecorView());
        mParentView_rl = (RelativeLayout) findViewById(R.id.activity_linear_layout_params_rl);
        mParentView_ll = (LinearLayout) findViewById(R.id.activity_linear_layout_params);
        mMultipleProperties_tv = (TextView) findViewById(R.id.multiple_properties_tv);
    }

    @Override
    public String getUrl() {
        return null;
    }

    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1~100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                //获得当前进度占整个动画过程的比例，浮点型，0~1之间
                float fraction = animator.getAnimatedFraction();
                //直接调用整型估值器，通过比例计算宽度，然后再设给Button
                ViewGroup.MarginLayoutParams params=(ViewGroup.MarginLayoutParams)(target.getLayoutParams());
                params.leftMargin= mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(5000).start();
    }
}
