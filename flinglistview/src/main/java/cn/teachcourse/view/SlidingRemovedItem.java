package cn.teachcourse.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 钊林IT on 2018/1/29.
 */

public class SlidingRemovedItem extends ViewGroup {
    protected static final String TAG = "SlidingRemovedItem";

    public SlidingRemovedItem(Context context) {
        this(context, null);
    }

    public SlidingRemovedItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRemovedItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TextView deleteButton_tv = createDeleteButton();
        this.addView(deleteButton_tv, 0);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        if (getChildCount() > 2) {
            throw new IndexOutOfBoundsException("Sub views is too many.");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View tvDelete = getChildAt(0);
        int tvDeleteWidth = tvDelete.getMeasuredWidth();
        int tvDeleteHeight = tvDelete.getMeasuredHeight();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvDelete.getLayoutParams();
        int height = lp.height;
        lp.gravity = Gravity.CENTER_VERTICAL;
        //设置删除按钮的布局位置
        tvDelete.layout(0, height / 2 - tvDeleteHeight / 2, tvDeleteWidth, height / 2 + tvDeleteHeight / 2);
        //设置item的布局位置
        getChildAt(1).layout(tvDeleteWidth, 0, getMeasuredWidth(), getMeasuredHeight());
        //隐藏删除按钮
        this.scrollTo(tvDeleteWidth, 0);
    }
    /**
     * 返回删除按钮
     *
     * @return
     */
    public View getDeleteButton() {
        return getChildAt(0);
    }

    /**
     * 测量宽度，额外添加删除按钮的宽度
     *
     * @param widthMeasureSpec
     * @return
     */
    protected int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        int tvDeleteWidth = 0;
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else if (mode == MeasureSpec.UNSPECIFIED
                || mode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < getChildCount(); i++) {
                if (i == 1)
                    width = getChildAt(i).getMeasuredWidth();
            }
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0)
                tvDeleteWidth = getChildAt(i).getMeasuredWidth();
        }
        Log.i(TAG, "width:" + width);
        Log.i(TAG, "tvDeleteWidth:" + tvDeleteWidth);
        return width + tvDeleteWidth;
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    protected int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else if (mode == MeasureSpec.UNSPECIFIED
                || mode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < getChildCount(); i++) {
                if (i == 1) {
                    height = getChildAt(i).getMeasuredHeight();
                }
            }
        }
        //重新调整删除按钮的高度
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0)
                getChildAt(0).setLayoutParams(lp);
        }
        Log.i(TAG, "height:" + height);
        return height;
    }

    /**
     * 创建删除按钮
     *
     * @return
     */
    protected TextView createDeleteButton() {
        TextView tvDelete = new TextView(getContext());
        tvDelete.setText("删除");
        tvDelete.setGravity(Gravity.CENTER);
        tvDelete.setBackgroundColor(Color.RED);
        tvDelete.setTextColor(Color.WHITE);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (10 * scale + 0.5f);
        tvDelete.setPadding(px * 2, px, px * 2, px);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.CENTER_VERTICAL;//绘制的时候让TextView垂直居中
        tvDelete.setLayoutParams(lp);
        tvDelete.setClickable(true);
        return tvDelete;
    }
}
