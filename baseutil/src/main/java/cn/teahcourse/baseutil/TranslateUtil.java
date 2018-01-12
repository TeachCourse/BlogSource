package cn.teahcourse.baseutil;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by http://teachcourse.cn on 2017/12/20.
 */

public class TranslateUtil {
    boolean visible = true;

    public void translate(View view) {
        if (visible) {
            visible = false;
            //相对于自己的高度往下平移
            TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            translate.setDuration(500);//动画时间500毫秒
            translate.setFillAfter(true);//动画出来控件可以点击
            view.startAnimation(translate);//开始动画
            view.setVisibility(View.VISIBLE);//设置可见

        } else {
            visible = true;
            //相对于自己的高度往上平移
            TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
            translate.setDuration(500);
            translate.setFillAfter(false);//设置动画结束后控件不可点击
            view.startAnimation(translate);
            view.setVisibility(View.GONE);//隐藏不占位置
        }
    }

    public TranslateAnimation translateIn() {
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translate.setDuration(500);//动画时间500毫秒
        translate.setFillAfter(true);//动画出来控件可以点击
        return translate;
    }

    public TranslateAnimation translateOut() {
        TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        translate.setDuration(500);
        translate.setFillAfter(false);//设置动画结束后控件不可点击
        return translate;
    }
}
