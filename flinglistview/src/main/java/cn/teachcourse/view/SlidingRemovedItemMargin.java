package cn.teachcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 钊林IT on 2018/2/2.
 */

public class SlidingRemovedItemMargin extends SlidingRemovedItem {
    public SlidingRemovedItemMargin(Context context) {
        super(context);
    }

    public SlidingRemovedItemMargin(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingRemovedItemMargin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取父容器宽度和高度
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int tempWidth = 0;
        int tempHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            tempHeight = view.getMeasuredHeight();
            tempHeight = Math.max(height, tempHeight);
            // 获取父容器外边距
            CustomLayoutParams clp = (CustomLayoutParams) view.getLayoutParams();
            int marginLeft = clp.leftMargin;
            int marginTop = clp.topMargin;
            int marginRight = clp.rightMargin;
            int marginBottom = clp.bottomMargin;
            if (i == 0) {
                tempWidth = view.getMeasuredWidth();
                view.layout(marginLeft, marginTop, tempWidth + marginLeft, tempHeight - marginBottom);
                //隐藏删除按钮(将当前ViewGroup往左边滚动删除按钮宽度的距离)
                this.scrollTo(tempWidth, 0);
                tempWidth += marginLeft + marginRight;//加上外边距
            }
            if (i == 1) {
                view.layout(tempWidth + marginLeft, marginTop, width, tempHeight - marginBottom);
            }
            Log.d(TAG, "--------->" + tempWidth);
        }
        Log.d(TAG, "-------->height=" + height + ";tempHeight=" + tempHeight);
    }

    /**
     * 测量宽度，额外添加删除按钮的宽度（添加margin参数）
     *
     * @param widthMeasureSpec
     * @return
     */
    @Override
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
                if (i == 1) {
                    View child = getChildAt(i);
                    // 获取子元素的布局参数
                    CustomLayoutParams clp = (CustomLayoutParams) child.getLayoutParams();

                    // 测量子元素并考虑外边距
                    measureChildWithMargins(child, widthMeasureSpec, 0, 0, 0);
                    width = child.getMeasuredWidth() + clp.leftMargin + clp.rightMargin;
                }
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0) {
                View child = getChildAt(i);
                // 获取子元素的布局参数
                CustomLayoutParams clp = (CustomLayoutParams) child.getLayoutParams();
                // 测量子元素并考虑外边距
                measureChildWithMargins(child, widthMeasureSpec, 0, 0, 0);
                tvDeleteWidth = child.getMeasuredWidth() + clp.leftMargin + clp.rightMargin;
            }
        }
        Log.i(TAG, "width:" + width);
        Log.i(TAG, "tvDeleteWidth:" + tvDeleteWidth);

        return width + tvDeleteWidth;
    }

    /**
     * 测量高度（添加margin参数）
     *
     * @param heightMeasureSpec
     * @return
     */
    @Override
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
                    View child = getChildAt(i);
                    // 获取子元素的布局参数
                    CustomLayoutParams clp = (CustomLayoutParams) child.getLayoutParams();
                    // 测量子元素并考虑外边距
                    measureChildWithMargins(child, 0, 0, heightMeasureSpec, 0);
                    // 测量子元素并考虑外边距
                    height = child.getMeasuredHeight() + clp.topMargin + clp.bottomMargin;
                }
            }
        }
        //重新调整删除按钮的高度
        CustomLayoutParams lp = new CustomLayoutParams(LayoutParams.WRAP_CONTENT, height);
        lp.setMargins(10, 10, 10, 10);//设置删除按钮的外边距
        for (int i = 0; i < getChildCount(); i++) {
            if (i == 0) {
                View child = getChildAt(i);
                child.setLayoutParams(lp);
                // 获取子元素的布局参数
                CustomLayoutParams clp = (CustomLayoutParams) child.getLayoutParams();
                // 测量子元素并考虑外边距
                measureChildWithMargins(child, 0, 0, heightMeasureSpec, 0);
                tvDeleteHeight = child.getMeasuredHeight() + clp.topMargin + clp.bottomMargin;
            }
        }
        height = Math.max(height, tvDeleteHeight);

        Log.i(TAG, "height:" + height);
        return height;
    }


    /**
     * 生成默认的布局参数
     */
    @Override
    protected CustomLayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 生成布局参数
     * 将布局参数包装成我们的
     */
    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    /**
     * 生成布局参数
     * 从属性配置中生成我们的布局参数
     */
    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    /**
     * 检查当前布局参数是否是我们定义的类型这在code声明布局参数时常常用到
     */
    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }

    /**
     * 定义一个内部类继承MarginLayoutParams，里面添加新的行为
     */
    public static class CustomLayoutParams extends MarginLayoutParams {

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
