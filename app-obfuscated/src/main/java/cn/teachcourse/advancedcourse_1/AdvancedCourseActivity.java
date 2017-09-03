package cn.teachcourse.advancedcourse_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.teachcourse.advancedcourse_1.bean.Subject;
import cn.teachcourse.obfuscateapplication.R;

public class AdvancedCourseActivity extends AppCompatActivity {
    private static final String TAG = "AdvancedCourseActivity";
    private Subject subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_course);
        subject=new Subject("Android混淆课程","2.0");
        initData();
    }

    private void initData() {
        Log.d(TAG, "initData: courseName="+subject.getCourseName()+"; creditHour="+subject.getCreditHour());
    }
}
