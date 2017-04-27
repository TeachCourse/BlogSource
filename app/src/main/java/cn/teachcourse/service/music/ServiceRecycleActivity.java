package cn.teachcourse.service.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class ServiceRecycleActivity extends BaseActivity {
    private Button mStartService_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_recycle);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
