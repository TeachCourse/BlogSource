package cn.teachcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 钊林IT on 2018/2/1.
 */

public class SlidingRemovedItemCenterVertical extends SlidingRemovedItemPadding {
    private static final String TAG = "SlidingRemovedItem";

    public SlidingRemovedItemCenterVertical(Context context) {
        this(context, null);
    }

    public SlidingRemovedItemCenterVertical(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRemovedItemCenterVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 获取父容器内边距
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        //获取父容器宽度和高度
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int tempWidth = 0;
        int tempHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            tempHeight = view.getMeasuredHeight();
            Log.d(TAG, "-------->height=" + height + ";tempHeight(" + i + ")=" + tempHeight);
            if (i == 0) {
                tempWidth = view.getMeasuredWidth();
//                将子控件高度定位为父容器直接，然后添加padding参数(垂直居中)
                view.layout(paddingLeft, height / 2 - tempHeight / 2 + paddingTop, tempWidth + paddingLeft, height / 2 + tempHeight / 2 + paddingTop);
            }
            if (i == 1) {
                view.layout(tempWidth + paddingLeft, height / 2 - tempHeight / 2 + paddingTop, width, height / 2 + tempHeight / 2 + paddingTop);
            }
        }
        //隐藏删除按钮（将当前ViewGroup往左边滚动删除按钮宽度的距离）
        this.scrollTo(getChildAt(0).getMeasuredWidth(), 0);
    }

}
