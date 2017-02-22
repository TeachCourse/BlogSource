package cn.teachcourse.transition;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.teachcourse.R;

public class TransitionActivity extends AppCompatActivity {
    private ImageView imageView;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent(context, TransitionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        imageView = (ImageView) findViewById(R.id.imageView);
//        startTransition();
        reverseTransition();

    }

    private void startTransition() {
        /**
         * 使用xml方式添加图片的过渡效果
         */

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transitionDrawable = (TransitionDrawable) imageView.getDrawable();
                transitionDrawable.startTransition(1500);
            }
        });
    }

    private void reverseTransition() {
        /**
         * 使用代码的方式添加图片的过渡效果
         */
        findViewById(R.id.reverse_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{getResources().getDrawable(R.drawable.beautiful_girl_003), getResources().getDrawable(R.drawable.beautiful_girl_004)});
                transitionDrawable.setCrossFadeEnabled(false);
                imageView.setImageDrawable(transitionDrawable);
                transitionDrawable.reverseTransition(1500);
            }
        });
    }
}
