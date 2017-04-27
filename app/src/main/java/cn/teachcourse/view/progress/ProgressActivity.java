package cn.teachcourse.view.progress;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.view.MyTextViewActivity;

public class ProgressActivity extends BaseActivity {
    private ProgressBar mProgressBar;
    private static final int MAX_VALUE=50000;//进度条的最大值
    private int mProgressStatus=0;//当前进度
    private Handler mHandler=new Handler();

    public static void start(Context context)
    {
        start(context, null);
    }

    public static void start(Context context, Intent extras)
    {
        Intent intent = new Intent();
        intent.setClass(context, ProgressActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        initView();
        initData();
    }

    private void initData() {
        mProgressBar.setMax(MAX_VALUE);
        new Thread(new Runnable() {
            public void run() {
                if(mProgressStatus>=MAX_VALUE){
                    mProgressStatus=0;
                }else{
                    while (mProgressStatus < MAX_VALUE) {
                        mProgressStatus++;

                        // Update the progress bar
                        mHandler.post(new Runnable() {
                            public void run() {
                                mProgressBar.setProgress(mProgressStatus);
                            }
                        });
                    }
                }

            }
        }).start();
    }

    private void initView() {
        initCommon(getWindow().getDecorView());
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
