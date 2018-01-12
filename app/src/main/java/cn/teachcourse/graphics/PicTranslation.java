package cn.teachcourse.graphics;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.teachcourse.R;

public class PicTranslation extends AppCompatActivity {

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent(context, PicTranslation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_transition);


        startTransition();
        reverseTransition();

    }

    /**
     * 使用xml方式添加图片的过渡效果
     */
    private void startTransition() {
        final ImageView normal_iv = (ImageView) findViewById(R.id.normal_iv);
        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) normal_iv.getDrawable();
                transitionDrawable.startTransition(4500);
                /*反转转换，在当前转换的位置上*/
//                transitionDrawable.reverseTransition(3500);
            }
        });
    }

    /**
     * 使用代码的方式添加图片的过渡效果
     */
    private void reverseTransition() {
        final ImageView imageView = (ImageView) findViewById(R.id.reverse_iv);
        findViewById(R.id.reverse_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.beautiful_girl_001), getResources().getDrawable(R.drawable.beautiful_girl_002)});
//                imageView.setImageDrawable(transitionDrawable);
//                transitionDrawable.setCrossFadeEnabled(false);
//                transitionDrawable.reverseTransition(1500);
                TransitionDrawable transitionDrawable = (TransitionDrawable) imageView.getDrawable();
                transitionDrawable.reverseTransition(3500);
            }
        });
    }
}
