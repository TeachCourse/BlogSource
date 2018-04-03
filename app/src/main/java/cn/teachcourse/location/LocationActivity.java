package cn.teachcourse.location;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teahcourse.baseutil.LocationUtil;
import cn.teahcourse.baseutil.ToastUtil;

/**
 * Created by http://teachcourse.cn on 2017/9/28.
 */

public class LocationActivity extends BaseActivity {
    private TextView currentLocation_tv;
    private LocationUtil mLocationUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        initData();
    }

    private void initData() {
        mLocationUtil = new LocationUtil(this);
        Location location = mLocationUtil.getLocation();
        if (location == null) {
            mLocationUtil.initLocation();
            ToastUtil.getInstance(getBaseContext()).show("获取当前位置失败");
        } else {
            currentLocation_tv.setText("latitude=" + location.getLatitude() + "\n" + "longitude=" + location.getLongitude());
        }
    }

    private void initView() {
        currentLocation_tv = (TextView) findViewById(R.id.current_location_info_tv);
    }
}
