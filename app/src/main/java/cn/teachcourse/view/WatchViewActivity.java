package cn.teachcourse.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class WatchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Builder builder=new WatchViewImpl.BuilderImpl(this);
        builder.setRadius(300f).setMinuteColor(Color.BLUE).setSecondColor(Color.RED).setHourColor(0xff999999);
//        builder.setPadding(10f);
//        builder.setShortScaleColor(Color.WHITE);
        WatchViewImpl watchView=builder.create();
        setContentView(watchView);
//        setContentView(R.layout.activity_watch_view);
    }

}
