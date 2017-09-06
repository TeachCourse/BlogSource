package cn.teachcourse.enums.season;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.enums.original.ExcellentStudent;
import cn.teachcourse.enums.original.ExcellentStudentEnum;
import cn.teachcourse.enums.week.WeeklyPlanEnum;

public class SeasonInfoActivity extends AppCompatActivity {
    private static final String TAG = "SeasonInfoActivity";
    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_info);
        initView();
        SeasonEnum season = SeasonEnum.AUTUMN;
        printDesc(season);
        test();
        testExcellent();
    }

    private void printDesc(SeasonEnum season) {
        if (SeasonEnum.SPRINT == season) {
            Log.d(TAG, "printDesc: 春天，春来江水绿如蓝");
        } else if (SeasonEnum.SUMMER == season) {
            Log.d(TAG, "printDesc: 夏天，牧童骑黄牛，歌声振林樾");
        } else if (SeasonEnum.AUTUMN == season) {
            Log.d(TAG, "printDesc: 秋天，夕阳西下，断肠人在天涯");
        } else {
            Log.d(TAG, "printDesc: 冬天，遥知不是雪，为有暗香来");
        }
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.choose_season);
        textView = (TextView) findViewById(R.id.description_tv);
        final String[] items = getResources().getStringArray(R.array.seasons);
        final String[] description = getResources().getStringArray(R.array.season_description);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(description[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void test() {
        /*1、测试name()方法，返回枚举常量的名字*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.name());
        /*2、测试ordinal()方法，返回枚举序数*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.ordinal());
        /*3、测试toString()方法，返回枚举常量的名字*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.toString());
        /*4、测试equals(Object other)方法，比较传入的对象是否等于当前的枚举常量*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.equals(ExcellentStudentEnum.LIUBEI));
        /*5、测试hashCode()方法，返回当前枚举常量的哈希码*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.hashCode());
        /*6、测试compareTo(E o)方法，比较当前枚举对象和传入的对象的顺序对象*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.compareTo(ExcellentStudentEnum.ZHAOYUN));
        Log.d(TAG, "test: " + ExcellentStudentEnum.ZHAOYUN.compareTo(ExcellentStudentEnum.ZHAOYUN));
        Log.d(TAG, "test: " + ExcellentStudentEnum.ZHAOYUN.compareTo(ExcellentStudentEnum.ZHANGFEI));
        /*7、测试getDeclaringClass()方法，返回enum标识的枚举的Class名称*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.LIUBEI.getDeclaringClass());
        /*8、valueOf(Class<T>,String) 是一个类方法，返回枚举常量的名字*/
        Log.d(TAG, "test: " + ExcellentStudentEnum.valueOf(ExcellentStudentEnum.LIUBEI.getDeclaringClass(), "LIUBEI"));
        Log.d(TAG, "test: " + ExcellentStudentEnum.valueOf(ExcellentStudentEnum.LIUBEI.getDeclaringClass(), "ZHANGFEI"));
        Log.d(TAG, "test: " + ExcellentStudentEnum.valueOf(ExcellentStudentEnum.LIUBEI.getDeclaringClass(), "ZHAOYUN"));
        /*抛出异常：xiaosan is not a constant in cn.teachcourse.enums.original.ExcellentStudentEnum*/
//        Log.d(TAG, "test: "+ExcellentStudentEnum.valueOf(ExcellentStudentEnum.ZHAOYUN.getDeclaringClass(),"xiaosan"));
        /*9、测试values()方法，返回当前枚举数组*/
        for (ExcellentStudentEnum student : ExcellentStudentEnum.values()) {
            Log.d(TAG, "test: " +student.getProfession());
        }
        /*10、测试valueOf(String)，返回指定枚举常量名字的枚举对象*/
        Log.d(TAG, "test: "+ExcellentStudentEnum.valueOf("LIUBEI").getNum());
        Log.d(TAG, "test: "+ExcellentStudentEnum.valueOf("ZHAOYUN").getProfession());
        /*抛出异常：xiaosan is not a constant in cn.teachcourse.enums.original.ExcellentStudentEnum*/
//        Log.d(TAG, "test: "+ExcellentStudentEnum.valueOf("xiaosan"));
    }

    private void testExcellent(){
        /*1、测试values()方法，返回当前类成员属性*/
        for (ExcellentStudent student:ExcellentStudent.values()) {
            Log.d(TAG, "testExcellent: " + student.getProfession());
        }
        /*2、测试valueOf()，返回指定名称的实例对象*/
        Log.d(TAG, "testExcellent: "+ExcellentStudent.valueOf("赵云").getNum());
    }
}
