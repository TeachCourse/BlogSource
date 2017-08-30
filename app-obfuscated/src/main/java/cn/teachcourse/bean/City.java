package cn.teachcourse.bean;

/**
 * Created by http://teachcourse.cn on 2017/8/29.
 */

public class City {
    private double latitude;
    private double longitude;
    private String cityName;

    public City(double latitude, double longitude, String cityName) {
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

    public static String from(){
        return "Chinese";
    }
}
