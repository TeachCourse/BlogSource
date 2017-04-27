package cn.teachcourse.rect;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.utils.DensityUtil;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/6.
 */

public class RectSimpleSummaryActivity extends BaseActivity {
    public static final int ANIM_WHAT = 0x110;
    private TextView mTextView;
    private Rect mRect = new Rect();
    private View button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

//        initViews();

    }

    private void initViews() {
        setContentView(R.layout.activity_simple_summary);
        mTextView = (TextView) findViewById(R.id.display_coordinate_tv);


        button = findViewById(R.id.calculate_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(printCoordinate());
            }
        });
        /**
         * 延时获取控件相对父容器的left、top、right、bottom坐标，否则为0
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                saveCoordinateToRect();
            }
        }).start();
    }

    private void initView() {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(Color.parseColor("#4682B4"));

        final TextView textView = new TextView(this);
        textView.setText("显示Rect存储坐标数据");
        /**
         * 设置TextView的宽度和高度
         */
        textView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
        textView.setBackgroundColor(Color.parseColor("#00BFFF"));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int top = v.getTop();
                int left = v.getLeft();
                int right = v.getRight();
                int bottom = v.getBottom();
                /**
                 * 将TextView相对父控件的坐标保存在Rect对象
                 */
                mRect.left = left;
                mRect.right = right;
                mRect.top = top;
                mRect.bottom = bottom;

//                textView.setText(mRect.toShortString());
                textView.setText(mRect.flattenToString());
            }
        });
        ll.addView(textView);

        final Button button = new Button(this);
        /**
         * 设置button的宽度和高度
         */
        ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(800, 300);
        /**
         * 设置button的margin属性值
         */
        params.setMargins(100,DensityUtil.dip2px(this,100),100,100);
        button.setLayoutParams(params);
        button.setText("计算Rect坐标");
        button.setBackgroundColor(Color.parseColor("#7FFFAA"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int top = v.getTop();
                int left = v.getLeft();
                int right = v.getRight();
                int bottom = v.getBottom();
                /**
                 * 将TextView相对父控件的坐标保存在Rect对象
                 */
                mRect.left = left;
                mRect.right = right;
                mRect.top = top;
                mRect.bottom = bottom;

                button.setText("宽度："+mRect.width()+"\n"+"高度："+mRect.height());
            }
        });

        ll.addView(button);


        final Button anim_btn =new Button(this);
        /**
         * 设置button的宽度和高度
         */
        params=new ViewGroup.MarginLayoutParams(800, 300);
        /**
         * 设置button的margin属性值
         */
        params.setMargins(100,DensityUtil.dip2px(this,100),100,100);
        anim_btn.setLayoutParams(params);
        anim_btn.setText("计算Rect坐标");
        anim_btn.setBackgroundColor(Color.parseColor("#DDA0DD"));
        anim_btn.setGravity(Gravity.RIGHT);
        anim_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int top = v.getTop();
                int left = v.getLeft();
                int right = v.getRight();
                int bottom = v.getBottom();
                /**
                 * 将TextView相对父控件的坐标保存在Rect对象
                 */
                mRect.left = left;
                mRect.right = right;
                mRect.top = top;
                mRect.bottom = bottom;
//                anim_btn.setText("水平中心点："+mRect.exactCenterX()+"\n垂直中心点："+mRect.exactCenterY());
                mRect.inset(10,10);
                anim_btn.setLeft(mRect.left);
                anim_btn.setTop(mRect.top);
                anim_btn.setRight(mRect.right);
                anim_btn.setBottom(mRect.bottom);
                anim_btn.invalidate();
            }
        });

        ll.addView(anim_btn);

        setContentView(ll);
    }

    private void saveCoordinateToRect() {
        int top = mTextView.getTop();
        int left = mTextView.getLeft();
        int right = mTextView.getRight();
        int bottom = mTextView.getBottom();
        /**
         * 将TextView相对父控件的坐标保存在Rect对象
         */
        mRect.left = left;
        mRect.right = right;
        mRect.top = top;
        mRect.bottom = bottom;
    }

    /**
     * 坐标转换
     * @return 返回左上角和右下角的坐标
     */
    private String printCoordinate() {
        saveCoordinateToRect();
        if (mRect.isEmpty())
            mTextView.setText("坐标获取为空");
        return mRect.toShortString();
    }

    @Override
    public String getUrl() {
        return "http://teachcourse.cn/2268.html";
    }
}
