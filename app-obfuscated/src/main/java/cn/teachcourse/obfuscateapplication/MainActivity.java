package cn.teachcourse.obfuscateapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.teachcourse.bean.City;
import cn.teachcourse.bean.Student;
import cn.teachcourse.bean.User;
import cn.teachcourse.bean.serialbean.CityBean;
import cn.teachcourse.bean.serialbean.UserBean;
import cn.teachcourse.obfuscateapplication.common.City2;
import cn.teachcourse.obfuscateapplication.main.User2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private User user;
    private City city;
    private User2 user2;
    private City2 city2;
    private CityBean cityBean;
    private UserBean userBean;
    private Student student;
    private static final int[] items={R.drawable.foreign_girl,R.drawable.local_girl};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=new User("TeachCourse","123456","广东省广州市创意园");
        user2 = new User2("TeachCourse", "123456", "广东省广州市创意园");
        userBean = new UserBean("TeachCourse", "123456", "广东省广州市创意园");

        city=new City(23.136620,113.329391,"广州");
        city2 = new City2(23.136620, 113.329391, "广州");

        cityBean=new CityBean(23.136620,113.329391,"深圳");
        student=new Student.Builder().build();
        initData();
    }

    private void initData() {
        Log.d(TAG, "initData: name=" + user.getName() + "；password=" + user.getPsw());
        Log.d(TAG, "initData: name=" + user2.getName() + "；password=" + user2.getPsw());
        Log.d(TAG, "initData: name=" + userBean.getName() + "；password=" + userBean.getPsw());

        Log.d(TAG, "initData: latitude="+city.getLatitude()+"；longitude="+city.getLongitude());
        Log.d(TAG, "initData: latitude=" + city2.getLatitude() + "；longitude=" + city2.getLongitude());
        Log.d(TAG, "initData: latitude="+cityBean.getLatitude()+"；longitude="+cityBean.getLongitude());

        Log.d(TAG, "initData: foreign girl="+items[0]+"；local girl="+items[1]);
        Log.d(TAG, "initData: name="+student.getName()+"；age="+student.getAge()+"；college="+student.getCollege());
        Log.d(TAG, "initData: All cities is from "+City.from());
    }
}
