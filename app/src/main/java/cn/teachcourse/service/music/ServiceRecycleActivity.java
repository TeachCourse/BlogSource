package cn.teachcourse.service.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cn.teachcourse.R;

public class ServiceRecycleActivity extends AppCompatActivity {
    private Button mStartService_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_recycle);
    }
}
