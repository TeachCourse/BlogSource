package cn.teahcourse.baseutil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * Created by http://teachcourse.cn on 2017/9/19.
 */

public class LocationUtil {
    private static final String TAG = "LocationUtil";
    public static final int REQUEST_LOCATION_CODE = 0x1;
    private Context mContext;
    private Location mLocation;
    private LocationManager mLocationManager;

    public LocationUtil(Context context) {
        this.mContext = context;
        mLocationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        init();
    }

    private void init() {
        boolean isOpenLocation = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isOpenLocation) {
            ToastUtil.getInstance(mContext).show("请开启定位服务");
            Intent intent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            (mContext).startActivity(intent);
        }
        initLocation();
    }

    public void initLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            initPermission();
        } else {
            mLocation = mLocationManager.getLastKnownLocation(getProvider());
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public void initPermission() {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_CODE);
    }

    /**
     * 获取Location Provider
     */
    private String getProvider() {
        // 构建位置查询条件
        Criteria criteria = new Criteria();
        // 查询精度：高
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 是否查询海拨：否
        criteria.setAltitudeRequired(false);
        // 是否查询方位角 : 否
        criteria.setBearingRequired(false);
        // 是否允许付费：是
        criteria.setCostAllowed(true);
        // 电量要求：低
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 返回最合适的符合条件的provider，第2个参数为true说明 , 如果只有一个provider是有效的,则返回当前provider
        return mLocationManager.getBestProvider(criteria, true);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null)
                setLocation(location);
            LogUtil.d(TAG,"----------->latitude="+location.getLatitude());
            LogUtil.d(TAG,"----------->longitude="+location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocation = mLocationManager.getLastKnownLocation(getProvider());
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        }

        @Override
        public void onProviderDisabled(String provider) {
            setLocation(null);
        }
    };

}
