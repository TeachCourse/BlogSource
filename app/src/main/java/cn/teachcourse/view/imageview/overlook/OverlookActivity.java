package cn.teachcourse.view.imageview.overlook;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;


/**
 * Created by Administrator on 2016/4/27.
 */
public class OverlookActivity extends BaseActivity {
    private KenBurnsView mKenBurnsView;//俯瞰的自定义View
    private int drawables[]=new int[]{R.drawable.background_one,R.drawable.background_two,R.drawable.background_three};
    private int which=0;//默认展示的背景图片
    private Drawable mDrawable;
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent =new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setClass(context,OverlookActivity.class);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlook_imageview);
        initView();
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        mKenBurnsView=(KenBurnsView)findViewById(R.id.ken_image);

        //添加过渡监听器
        mKenBurnsView.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if(which>=drawables.length-1){
                    which=0;
                }
                mDrawable=getResources().getDrawable(drawables[which++]);
                mKenBurnsView.setImageDrawable(mDrawable);
            }
        });
    }

    @Override
    public String getUrl() {
        return null;
    }
}
