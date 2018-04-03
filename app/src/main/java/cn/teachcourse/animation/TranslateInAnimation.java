package cn.teachcourse.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class TranslateInAnimation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_in_animation);
        initView();
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            boolean visible = false;

            @Override
            public void onClick(View v) {
                if (visible) {
                    visible = false;
                    ObjectAnimator.ofFloat(mBrowserBtn_rl, "translationY", 0, mBrowserBtn_rl.getHeight()).setDuration(500).start();
                    mBrowserBtn_rl.setVisibility(View.VISIBLE);
                } else {
                    visible = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(mBrowserBtn_rl, "translationY", mBrowserBtn_rl.getHeight(), -mBrowserBtn_rl.getHeight()).setDuration(500);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mBrowserBtn_rl.setVisibility(View.GONE);
                        }
                    });
                    anim.start();
                }
            }
        });
    }
}
