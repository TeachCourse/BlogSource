package cn.teachcourse.view;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cn.teachcourse.R;
import cn.teachcourse.view.custom.DrawingLineView2;
import cn.teachcourse.view.custom.GuaGuaLeView;
import cn.teachcourse.view.custom.PorterDuffXferView;
import cn.teachcourse.view.custom.RadarView;
import cn.teachcourse.view.drawingtool.BitmapBuffer;
import cn.teachcourse.view.drawingtool.DrawingView;
import cn.teachcourse.view.drawingtool.LineDrawer;
import cn.teachcourse.view.drawingtool.OvalDrawer;
import cn.teachcourse.view.drawingtool.PointHistory;
import cn.teachcourse.view.drawingtool.PointModel;
import cn.teachcourse.view.drawingtool.RectDrawer;
import cn.teachcourse.view.drawingtool.ViewParams;

public class PorterDuffXferActivity extends AppCompatActivity implements View.OnClickListener {
    private View mView;
    private Button mClear_btn;
    private Button mRedo_btn;
    private Button mRestore_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //位图换算
        mView = new PorterDuffXferView(this);
        //刮刮乐游戏
        mView = new GuaGuaLeView(this);
        //雷达扫描效果
        mView = new RadarView(this);
        //绘制面板效果
        mView = new DrawingLineView2(this);
        //通过注入的方式选择画圆、椭圆、矩形、线条等
        mView = new DrawingView(this);
        
        setContentView(R.layout.activity_porter_duff_xfer);
        
        initView();
        addEvent();
    }

    private void addEvent() {
        mClear_btn.setOnClickListener(this);
        mRedo_btn.setOnClickListener(this);
        mRestore_btn.setOnClickListener(this);
    }

    private void initView() {
        mClear_btn = (Button) findViewById(R.id.clear_btn);
        mRedo_btn = (Button) findViewById(R.id.redo_btn);
        mRestore_btn = (Button) findViewById(R.id.restore_btn);
        mView = findViewById(R.id.drawing_view);

        if (mView instanceof DrawingView)
            ((DrawingView) mView).setShapeDrawer(new RectDrawer());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_btn:

                break;
            case R.id.redo_btn:
                BitmapBuffer.getInstance().redo();
                ViewParams.isRedo = true;
                mView.invalidate();
                break;
            case R.id.restore_btn:

                break;
        }
    }
}
