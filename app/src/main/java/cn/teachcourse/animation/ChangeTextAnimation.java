package cn.teachcourse.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.teachcourse.R;


public class ChangeTextAnimation extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_x);
        initView();
    }

    public void startAnimator(View view) {
        iniXMLAnimator();
        iniAnimator();
    }

    private void iniXMLAnimator() {
        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_decimal_value);
        anim.setTarget(mTextView);
        anim.start();
    }

    private void iniAnimator() {
        final ViewWrapper view = new ViewWrapper(mTextView);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "text", 10f, 100f).setDuration(3000);
        objectAnimator.start();
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.animator_x_tv);
    }

    private static class ViewWrapper {
        private TextView mTargetView;

        public ViewWrapper(TextView view) {
            this.mTargetView = view;
        }

        public float getText() {
            String text = mTargetView.getText().toString();
            Pattern pattern = Pattern.compile("\\d+\\.?\\d*|\\.\\d+");
            Matcher m = pattern.matcher(text);
            if (m.matches()) {
                return Float.valueOf(text);
            }
            return 0f;
        }

        public void setText(float value) {
            mTargetView.setText(String.valueOf(value));
        }

    }
}
