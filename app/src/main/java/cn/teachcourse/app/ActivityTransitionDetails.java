package cn.teachcourse.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

import cn.teachcourse.R;
import cn.teachcourse.api.DownloadImgAPI;

public class ActivityTransitionDetails extends AppCompatActivity {
    private static final String KEY_ID = "ViewTransitionValues:id";
    private String mName = "ducky";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(randomColor()));
        setContentView(R.layout.activity_transition_details);

        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        final ImageView previewPic = (ImageView) findViewById(R.id.transition_preview_iv);
        String name = getIntent().getStringExtra(KEY_ID);
        if (name != null)
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names,
                                                Map<String, View> sharedElements) {
                    sharedElements.put("hero", previewPic);
                }
            });
        DownloadImgAPI.setImageViewAware(previewPic, "drawable://" + ActivityTransition.iconId);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void finish(View v) {
        Intent intent = new Intent(this, ActivityTransition.class);
        intent.putExtra(KEY_ID, v.getTransitionName());
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,
                v, "hero");
        startActivity(intent, activityOptions.toBundle());
        finish();
    }
    private static int randomColor() {
        int red = (int)(Math.random() * 128);
        int green = (int)(Math.random() * 128);
        int blue = (int)(Math.random() * 128);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }
}
