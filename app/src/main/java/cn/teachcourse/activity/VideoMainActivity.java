package cn.teachcourse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.hardware.Camera;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

import cn.teachcourse.R;
import cn.teachcourse.api.MediaUtilAPI;
import cn.teachcourse.common.BaseActivity;

public class VideoMainActivity extends BaseActivity implements View.OnClickListener {
    private static final String LOG_TAG = "VideoMainActivity";

    private Button mStartRecording_btn;
    private Button mStartPlaying_btn;
    private SurfaceView mSurfaceView;

    private MediaUtilAPI mediaUtilAPI;

    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;

    private SurfaceHolder mSurfaceHolder;

    private Camera mCamera;
    boolean previewing = false;

    private VideoView mPlayer;
    private File mRootPath;
    private String mSavePath = "recordAudio.mp4";;
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context, MainActivity.class);
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
        setContentView(R.layout.activity_video_main);

        initView();
        getSurfaceHolder();
        saveAudioFiles();
    }

    private void saveAudioFiles() {
        if (!Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return;
        }
        mRootPath = Environment.getExternalStorageDirectory();
    }


    private void initView() {
        initCommon(getWindow().getDecorView());
        mStartPlaying_btn = (Button) findViewById(R.id.video_play_record);
        mStartRecording_btn = (Button) findViewById(R.id.video_start_record);
        mSurfaceView = (SurfaceView) findViewById(R.id.video_surfaceView);
        mPlayer = (VideoView) findViewById(R.id.video_play_videoView);

        mStartRecording_btn.setOnClickListener(this);
        mStartPlaying_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_start_record:
                onRecord(mStartRecording);
                if (mStartRecording) {
                    mStartRecording_btn.setText("正在拍摄。。。");
                } else {
                    mStartRecording_btn.setText("点击拍摄");
                }
                mStartRecording = !mStartRecording;
                Log.e(LOG_TAG, mStartRecording + "");
                break;
            case R.id.video_play_record:
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    mStartPlaying_btn.setText("正在播放。。。");
                } else {
                    mStartPlaying_btn.setText("点击播放");
                }
                mStartPlaying = !mStartPlaying;
                Log.e(LOG_TAG, mStartPlaying + "");
                break;
        }
    }

    private void onRecord(boolean start) {
        mSurfaceView.setVisibility(View.VISIBLE);
        mPlayer.setVisibility(View.GONE);
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mSurfaceView.setVisibility(View.GONE);
        mPlayer.setVisibility(View.VISIBLE);

        mPlayer.setVideoPath(mRootPath + "/" + mSavePath);
        mPlayer.start();

    }

    private void stopPlaying() {
        mPlayer.stopPlayback();
    }


    private void startRecording() {

        mediaUtilAPI = MediaUtilAPI.getInstance();
        mediaUtilAPI.setSurfaceView(mSurfaceHolder);
        mediaUtilAPI.startVideo(mSavePath);

    }

    private void stopRecording() {

        mediaUtilAPI.stopVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaUtilAPI.stopVideo();
    }
    private void getSurfaceHolder() {
        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
                mSurfaceHolder = holder;

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
                mSurfaceHolder = holder;

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceHolder = null;
                mSurfaceView = null;
                mediaUtilAPI.stopVideo();
            }
        });

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
