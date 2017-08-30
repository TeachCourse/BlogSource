package cn.teachcourse.bean.serialbean;

import java.io.Serializable;

/**
 * Created by http://teachcourse.cn on 2017/8/30.
 */

public class CityBean implements Serializable {
    private double latitude;
    private double longitude;
    private String cityName;

    public CityBean(double latitude, double longitude, String cityName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCityName() {
        return cityName;
    }
}
