package cn.teachcourse.location;

import android.location.Address;
import android.util.Log;

import java.util.Locale;

/**
 * Created by postmaster@teachcourse.cn on 2016/6/3.
 */
public class LocaleDemo {
    Locale locale=new Locale(Locale.CHINA.getLanguage());
    Address address=new Address(locale);

}
