package cn.teachcourse.builder;
/**
 * Created by postmaster@teachcourse.cn on 2017/4/23.
 */

public abstract class Builder {

    public abstract Builder setRadius(float radius) ;

    public abstract void setBackgroundExterColor(int backgroundExterColor) ;

    public abstract Builder setTextSize(float textSize) ;

    public abstract Builder setHourWidth(float hourWidth) ;

    public abstract Builder setMinuteWidth(float minuteWidth) ;

    public abstract Builder setSecondWidth(float secondWidth) ;

    public abstract Builder setLongScaleColor(int longScaleColor) ;

    public abstract Builder setShortScaleColor(int shortScaleColor) ;

    public abstract Builder setHourColor(int hourColor) ;

    public abstract Builder setMinuteColor(int minuteColor) ;

    public abstract Builder setSecondColor(int secondColor) ;

    public abstract Builder setBackgroundColor(int backgroundColor) ;

    public abstract Builder setPadding(float padding) ;


    public abstract WatchViewImpl create() ;
}
