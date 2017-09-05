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

}
