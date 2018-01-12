package cn.teachcourse.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teahcourse.baseutil.PermissionsUtils;

public class RecordAudioActivity extends BaseActivity implements OnClickListener {
    private static final String LOG_TAG = "AudioRecordTest";

    private Button mRecordButton;
    private Button mPlayButton;
    private TextView mSysTime_TV;
    private String mSavePath = null;
    private File mRootPath;
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mPlayer;
    boolean mStartRecording = true;
    boolean mStartPlaying = true;

    private int mAmplitudeValue;

    private LinearLayout mAudioViewState;
    private ImageView mDisplayImageView;

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
        setContentView(R.layout.activity_main);
        initView();

        saveAudioFiles();

    }

    private void saveAudioFiles() {
        mRootPath = Environment.getExternalStorageDirectory();

        mSavePath = mRootPath.getAbsolutePath() + "/recordAudio.3gp";
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        mRecordButton = (Button) findViewById(R.id.record_autio);
        mPlayButton = (Button) findViewById(R.id.play_autio);
        mSysTime_TV = (TextView) findViewById(R.id.system_time);
        mAudioViewState = (LinearLayout) findViewById(R.id.audio_ll_record_state);
        mDisplayImageView = (ImageView) findViewById(R.id.display_record_iv);

        mRecordButton.setOnClickListener(this);
        mPlayButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_autio:

                onRecord(mStartRecording);

                break;
            case R.id.play_autio:

                onPlay(mStartPlaying);

                break;
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
        if (mStartRecording) {
            mRecordButton.setText("正在录音。。。");
        } else {
            mRecordButton.setText("点击录音");
        }
        mStartRecording = !mStartRecording;
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
        if (mStartPlaying) {
            mPlayButton.setText("正在播放。。。");
        } else {
            mPlayButton.setText("点击播放");
        }
        mStartPlaying = !mStartPlaying;
    }

    private void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mSavePath);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void startRecording() {
        if (PermissionsUtils.hasPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mSavePath);
            try {
                mMediaRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
                e.printStackTrace();
            }
            mMediaRecorder.start();
        } else {
            PermissionsUtils.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }

    }

    private void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
        mSysTime_TV.setText("保存的路径：" + mSavePath);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public String getUrl() {
        return null;
    }
}

