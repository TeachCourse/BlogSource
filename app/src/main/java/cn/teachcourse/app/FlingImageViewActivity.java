package cn.teachcourse.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;

import cn.teachcourse.R;
import cn.teachcourse.view.imageview.large.FlingImageView;
import cn.teahcourse.baseutil.DensityUtil;
import cn.teahcourse.baseutil.DimensionUtil;

public class FlingImageViewActivity extends AppCompatActivity {
    private static final String TAG = "FlingImageViewActivity";
    private FlingImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling_image_view);
        initView();
        float dp=DimensionUtil.getUnitDip(this,1);
        int dip= DensityUtil.px2dip(this,1);
    }

    private void initView() {
        imageView = (FlingImageView) findViewById(R.id.fling_iv);
    }
}
