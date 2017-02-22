package cn.teachcourse.activity;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Environment;
import android.media.MediaRecorder;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * Created by Administrator on 2016/3/1.
 */
public class MediaUtilAPI {
    private transient static MediaUtilAPI instance = null;

    public static synchronized MediaUtilAPI getInstance() {
        if (instance == null) {
            instance = new MediaUtilAPI();
        }
        return instance;
    }

    private File file;
    private MediaRecorder mRecorder = null;

    private SurfaceHolder mSurfaceView;

    private Camera mCamera;
    public void startAudio(String name) {
        if (!Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return;
        }
        file = new File(android.os.Environment.getExternalStorageDirectory()
                + "/" + name);
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setAudioSamplingRate(8000);
            mRecorder.setAudioEncodingBitRate(16);
            mRecorder.setAudioChannels(1);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(file.getAbsolutePath());
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IllegalStateException e) {
                System.out.print(e.getMessage());
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }

        }
    }
    @SuppressLint("NewApi")
    public void startVideo(String name) {
        if (!Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return;
        }
        file = new File(android.os.Environment.getExternalStorageDirectory()
                + "/" + name);
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mCamera=Camera.open();
            mCamera.lock();
            mCamera.setDisplayOrientation(90);
            mCamera.unlock();
            mRecorder.setCamera(mCamera);//必须在setVideoSource()之前，否则异常
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mRecorder.setVideoSize(176, 144);
            mRecorder.setVideoFrameRate(20);
            Surface surface=getSurfaceView().getSurface();
            mRecorder.setPreviewDisplay(surface);
            mRecorder.setOutputFile(file.getAbsolutePath());
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (IllegalStateException e) {
                System.out.print(e.getMessage());
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }

        }
    }

    public void setSurfaceView(SurfaceHolder view){
        this.mSurfaceView=view;
    }

    public SurfaceHolder getSurfaceView(){
        return this.mSurfaceView;
    }
    public void stopAudio() {
        if (mRecorder != null) {
            try {
                mRecorder.setOnErrorListener(null);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        }
    }
    public void stopVideo() {
        if (mRecorder != null) {
            try {
                mRecorder.setOnErrorListener(null);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                //必须最后释放资源，否则报错
                mCamera.release();
                mCamera=null;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        }
    }
    /*
     * 获取振幅最大值
     *
     */
    public int getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 110;

    }
    /*
     * 封装成接口，实现setView()方法
     *
     */
    public void getAmplitude(AudioDisplayAnimationView view) {
                if (mRecorder!=null)
                view.setView(mRecorder.getMaxAmplitude());
    }

    public void setCamera(Camera camera){
        this.mCamera=camera;
    }

    public Camera getCamera() {
        return mCamera;
    }

    /*
     * 接口 AudioDisplayAnimationView
     *
     */
    public interface AudioDisplayAnimationView {
           public void setView(int amplitude);
    }
}
