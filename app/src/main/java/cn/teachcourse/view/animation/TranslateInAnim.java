package cn.teachcourse.view.animation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class TranslateInAnim extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_in_animation);
        initView();
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            boolean visible=true;
            @Override
            public void onClick(View v) {
                if (visible) {
                    visible = false;
                    //相对于自己的高度往下平移
                    TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    translate.setDuration(500);//动画时间500毫秒
                    translate.setFillAfter(true);//动画出来控件可以点击
                    mBrowserBtn_rl.startAnimation(translate);//开始动画
                    mBrowserBtn_rl.setVisibility(View.VISIBLE);//设置可见

                } else {
                    visible = true;
                    //相对于自己的高度往上平移
                    TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
                    translate.setDuration(500);
                    translate.setFillAfter(false);//设置动画结束后控件不可点击
                    mBrowserBtn_rl.startAnimation(translate);
                    mBrowserBtn_rl.setVisibility(View.GONE);//隐藏不占位置
                }
            }
        });
    }
}
