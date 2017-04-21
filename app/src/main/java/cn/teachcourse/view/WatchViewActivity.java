package cn.teachcourse.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

import cn.teachcourse.R;
import cn.teachcourse.utils.LogUtils;


public class WatchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WatchView.Builder builder=new WatchView.Builder(this);
        builder.setRadius(300f);
//        builder.setMinuteColor(Color.BLUE);
//        builder.setSecondColor(Color.RED);
//        builder.setHourColor(0xff999999);
//        builder.setPadding(10f);
        builder.setShortScaleColor(Color.WHITE);
        WatchView watchView=builder.create();
        setContentView(watchView);
//        setContentView(R.layout.activity_watch_view);
    }

}
