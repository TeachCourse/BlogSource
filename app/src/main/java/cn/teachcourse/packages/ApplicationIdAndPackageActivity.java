package cn.teachcourse.packages;

import android.os.Bundle;
import android.widget.TextView;

import cn.teachcourse.BuildConfig;
import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class ApplicationIdAndPackageActivity extends BaseActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_id_and_package);
        initView();
        initData();
    }

    private void initData() {
        if (BuildConfig.DEBUG){
            mTextView.setText("debug模式：getPackageName()="+getPackageName());
        }else{

            mTextView.setText("release模式：getPackageName()="+getPackageName());
        }
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        mTextView = (TextView) findViewById(R.id.textview);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
