package cn.teachcourse.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.api.MediaUtilAPI;
import cn.teachcourse.common.BaseActivity;

public class AudioMainActivity extends BaseActivity implements View.OnTouchListener {
    private static final String LOG_TAG = "AudioMainActivity";
    private Button mLongPressRecord_btn;
    private TextView mRecordTime_tv;
    private ImageView mDisplayImageView;
    private LinearLayout mAudioViewState;
    private MediaUtilAPI mMediaRecorder = null;
    private long saveRecordFileNameLong;// 获取当前时间做名字

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, RecordAudioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_main);
        initView();
        addEvent();
    }

    private void addEvent() {
        mLongPressRecord_btn.setOnTouchListener(this);
    }


    private void initView() {
        initButton(getWindow().getDecorView());
        mLongPressRecord_btn = (Button) findViewById(R.id.start_record_audio_btn);
        mDisplayImageView = (ImageView) findViewById(R.id.display_record_iv);
        mAudioViewState = (LinearLayout) findViewById(R.id.audio_ll_record_state);
        mRecordTime_tv = (TextView) findViewById(R.id.record_time_tv);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int flag = getPackageManager().checkPermission(android.Manifest.permission.RECORD_AUDIO, getPackageName());
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (PackageManager.PERMISSION_GRANTED == flag) {
                    mMediaRecorder = MediaUtilAPI.getInstance();
                    saveRecordFileNameLong = SystemClock.currentThreadTimeMillis();

                    mMediaRecorder.startAudio(saveRecordFileNameLong + ".amr");
                    mAudioViewState.setVisibility(View.VISIBLE);

                    getAmplitude();
                    Log.e(LOG_TAG, "长按按钮：------------------->");
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0x11);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (PackageManager.PERMISSION_GRANTED == flag) {
                    mMediaRecorder.stopAudio();
                    mAudioViewState.setVisibility(View.GONE);
                }

                break;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x11) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
                if (isSecondRequest)
                /**重新请求授予权限，显示权限说明（该说明属于系统UI内容，区别第一次弹窗）**/
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0x11);
                else
                    Toast.makeText(this, "录音权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };


    private void getAmplitude() {
        mHandler.postDelayed(mTaskRunable, 300);

    }

    private Runnable mTaskRunable = new Runnable() {
        @Override
        public void run() {
            mMediaRecorder.getAmplitude(new MediaUtilAPI.AudioDisplayAnimationView() {
                @Override
                public void setView(int amplitude) {
                    updateViewByAmplitudeValue(amplitude);

                    mHandler.postDelayed(mTaskRunable, 300);
                }
            });
        }
    };

    private void updateViewByAmplitudeValue(int values) {
        values = Math.round(values / 1000);
        Log.e(LOG_TAG, "Value=" + values);
        switch (values) {
            case 0:
                mDisplayImageView.setImageResource(R.drawable.mic_0);
                break;
            case 1:
                mDisplayImageView.setImageResource(R.drawable.mic_1);
                break;
            case 2:
                mDisplayImageView.setImageResource(R.drawable.mic_2);
                break;
            case 3:
                mDisplayImageView.setImageResource(R.drawable.mic_3);
                break;
            case 4:
                mDisplayImageView.setImageResource(R.drawable.mic_4);
                break;
            case 5:
                mDisplayImageView.setImageResource(R.drawable.mic_5);
                break;
            case 6:
                mDisplayImageView.setImageResource(R.drawable.mic_6);
                break;
            case 7:
                mDisplayImageView.setImageResource(R.drawable.mic_7);
                break;
        }
    }

    @Override
    public String getUrl() {
        return "http://teachcourse.cn/2319.html";
    }
}
