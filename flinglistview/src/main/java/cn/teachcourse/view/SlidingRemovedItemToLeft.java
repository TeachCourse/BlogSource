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
 * Created by 钊林IT on 2018/1/26.
 */

public class SlidingRemovedItemToLeft extends ViewGroup {
    private static final String TAG = "ExtendLayout";

    public SlidingRemovedItemToLeft(Context context) {
        this(context, null);
    }

    public SlidingRemovedItemToLeft(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRemovedItemToLeft(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TextView deleteButton_tv = createDeleteButton();
        this.addView(deleteButton_tv, 0);
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
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        //设置删除按钮的布局位置
        View tvDelete = getChildAt(0);
        int tvDeleteWidth = tvDelete.getMeasuredWidth();
        int tvDeleteHeight = tvDelete.getMeasuredHeight();
        //设置item的布局位置
        View itemView = getChildAt(1);
        int itemViewWidth = itemView.getMeasuredWidth();
        //定位布局
        tvDelete.layout(itemViewWidth, height / 2 - tvDeleteHeight / 2, width, height / 2 + tvDeleteHeight / 2);
        itemView.layout(-tvDeleteWidth, 0, itemViewWidth, height);
        //隐藏删除按钮
        this.scrollTo(-tvDeleteWidth, 0);
    }


    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        if (getChildCount() > 2) {
            throw new IndexOutOfBoundsException("Sub views is too many.");
        }
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
    private int measureWidth(int widthMeasureSpec) {
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
        return width + tvDeleteWidth;
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
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
                getChildAt(i).setLayoutParams(lp);
        }
        Log.i(TAG, "height:" + height);
        return height;
    }

    /**
     * 创建删除按钮
     *
     * @return
     */
    private TextView createDeleteButton() {
        TextView tvDelete = new TextView(getContext());
        tvDelete.setText("删除");
        tvDelete.setGravity(Gravity.CENTER);
        tvDelete.setBackgroundColor(Color.RED);
        tvDelete.setTextColor(Color.WHITE);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int px = (int) (10 * scale + 0.5f);
        tvDelete.setPadding(px * 2, px, px * 2, px);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        tvDelete.setLayoutParams(lp);
        tvDelete.setClickable(true);
        return tvDelete;
    }
}
