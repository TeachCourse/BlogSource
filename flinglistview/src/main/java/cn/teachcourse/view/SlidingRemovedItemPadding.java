package cn.teachcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 钊林IT on 2018/2/1.
 */

public class SlidingRemovedItemPadding extends SlidingRemovedItem {
    private static final String TAG = "SlidingRemovedItem";

    public SlidingRemovedItemPadding(Context context) {
        this(context, null);
    }

    public SlidingRemovedItemPadding(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRemovedItemPadding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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
        // 获取父容器内边距
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        //获取父容器宽度和高度
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//        width += paddingLeft;
//        height += paddingTop;

        int tempWidth = 0;
        int tempHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            tempHeight = view.getMeasuredHeight();
            tempHeight = Math.max(height, tempHeight);
            if (i == 0) {
                tempWidth = view.getMeasuredWidth();
                view.layout(paddingLeft, paddingTop, tempWidth + paddingLeft, tempHeight);
                //隐藏删除按钮(将当前ViewGroup往左边滚动删除按钮宽度的距离)
                this.scrollTo(tempWidth , 0);
            }
            if (i == 1) {

                view.layout(tempWidth + paddingLeft, paddingTop, width, tempHeight);
            }
        }
        Log.d(TAG, "-------->height=" + height + ";tempHeight=" + tempHeight);
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
        //考虑padding对测量宽度的影响
        width = Math.max(width, getSuggestedMinimumWidth());
        width += getPaddingLeft() + getPaddingRight();
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
        int tvDeleteHeight = 0;
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
        lp.gravity = Gravity.CENTER_VERTICAL;

        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0)
                tvDeleteHeight = getChildAt(i).getMeasuredWidth();
        }
        //考虑padding对测量高度的影响
        height += getPaddingTop() + getPaddingBottom();
        height = Math.max(height, tvDeleteHeight);
        Log.i(TAG, "height:" + height);
        return height;
    }

}
