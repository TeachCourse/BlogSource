package cn.teachcourse.scancode;

import java.io.IOException;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.view.webview.ZXingJSWebViewActivity;
import cn.teachcourse.scancode.camera.CameraManager;
import cn.teachcourse.scancode.decoding.CaptureActivityHandler;
import cn.teachcourse.scancode.decoding.InactivityTimer;
import cn.teachcourse.scancode.view.MessageIDs;
import cn.teachcourse.scancode.view.UISwitchButton;
import cn.teachcourse.scancode.view.ViewfinderView;


@SuppressLint("NewApi")
public class CaptureActivity extends BaseActivity implements Callback,
		View.OnClickListener, OnCheckedChangeListener {
	public static final String QR_RESULT = "RESULT";

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private SurfaceView surfaceView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	// private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private CameraManager cameraManager;
	private TextView tv, newBackTv;
	private Parameters parameter;
	private int sign;
	private String userFlage = "";// 标志从哪里过来的扫描，info 是绑定时候的
	private UISwitchButton switchUI;
	private ImageView scanImg;
	private String openOrClose = "0";// 0是close,1是open

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.zxing_capture_new);

		initView();
		initEvent();
	}

	/**
	 * 初始化布局控件
	 */
	private void initView() {
		initButton(getWindow().getDecorView());
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);
		switchUI = (UISwitchButton) findViewById(R.id.switch12);
		tv = (TextView) this.findViewById(R.id.tv_bt);
		scanImg = (ImageView) this.findViewById(R.id.scan_state_img);
		newBackTv = (TextView) this.findViewById(R.id.backTV_new);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		SetSwitchBtn();
	}

	/**
	 * 开启灯光
	 */
	private void SetSwitchBtn() {
		switchUI.setChecked(false);
		switchUI.setOnCheckedChangeListener(this);
		scanImg.setOnClickListener(this);
		newBackTv.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		cameraManager = new CameraManager(getApplication());

		viewfinderView.setCameraManager(cameraManager);

		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		if (null != cameraManager) {
			if (cameraManager.TheLightIsOnOrOff()) {
				switchUI.setChecked(true);
				scanImg.setImageResource(R.drawable.scan_open_img);
				openOrClose = "1";
			} else {
				switchUI.setChecked(false);
				scanImg.setImageResource(R.drawable.scan_close_img);
				openOrClose = "0";
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			cameraManager.openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**处理扫码识别到的二位的二维码信息
	 * @param obj
	 * @param barcode
	 */
	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		/**
		 * 根据扫描二维码内容，进行逻辑处理
		 */
		Intent intent=new Intent(this, ZXingJSWebViewActivity.class);
		intent.putExtra("flag","scanZXing");
		startActivityForResult(intent,0x110);
		Toast.makeText(this, obj.getText(), Toast.LENGTH_SHORT).show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {

			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			try {
				AssetFileDescriptor fileDescriptor = getAssets().openFd(
						"qrbeep.ogg");
				this.mediaPlayer.setDataSource(
						fileDescriptor.getFileDescriptor(),
						fileDescriptor.getStartOffset(),
						fileDescriptor.getLength());
				this.mediaPlayer.setVolume(0.1F, 0.1F);
				this.mediaPlayer.prepare();
			} catch (IOException e) {
				this.mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;
	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_CANCELED);
			finish();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_FOCUS
				|| keyCode == KeyEvent.KEYCODE_CAMERA) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 开关灯光效果
		 */
		case R.id.scan_state_img:
			if ("0".equals(openOrClose)) {// 0是close,1是open
				cameraManager.openF();
				scanImg.setImageResource(R.drawable.scan_open_img);
				openOrClose = "1";
			} else if ("1".equals(openOrClose)) {
				cameraManager.stopF();
				scanImg.setImageResource(R.drawable.scan_close_img);
				openOrClose = "0";
			}
			break;
		/**
		 * 关闭当前Activity
		 */
		case R.id.backTV_new:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			cameraManager.openF();
		} else {
			cameraManager.stopF();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==0x110){
			switch (resultCode){
				case RESULT_OK:
					finish();
					break;
			}
		}
	}

	@Override
	public String getUrl() {
		return null;
	}
}