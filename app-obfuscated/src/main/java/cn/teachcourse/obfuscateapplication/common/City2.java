package cn.teachcourse.obfuscateapplication.common;

/**
 * Created by http://teachcourse.cn on 2017/8/29.
 */

public class City2 {
    private double latitude;
    private double longitude;
    private String cityName;

    public City2(double latitude, double longitude, String cityName) {
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
