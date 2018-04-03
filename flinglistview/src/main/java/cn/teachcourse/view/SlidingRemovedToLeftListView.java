package cn.teachcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by 钊林IT on 2018/1/29.
 */

public class SlidingRemovedToLeftListView extends ListView {
    private static final String TAG = "FlingRemovedListView3";
    private static final int SNAP_VELOCITY = 600;
    private static final int SLIDE_MASK = 0x1;//是否可以滑动
    private static final int DELETE_VISIBLE_MASK = SLIDE_MASK << 1;//是否滑出删除按钮
    private int flags;//标识位
    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private float preX;    //手指滑动过程中上一个点的 x 坐标
    private float firstX, firstY;    //手指第一次按下的点的 x 坐标和 y 坐标
    private SlidingRemovedItemToLeft willFlingView;//要滑动的列表项 View
    private int position = INVALID_POSITION;//要滑动的列表项 View 的索引位置
    private int touchSlop;//最小滑动距离
    private OnItemRemovedListener onItemRemovedListener;

    public SlidingRemovedToLeftListView(Context context) {
        this(context, null);
    }

    public SlidingRemovedToLeftListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingRemovedToLeftListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        obtainVelocity(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished())
                    return super.dispatchTouchEvent(ev);
                firstX = preX = x;
                firstY = y;
                //确定点击的item
                position = this.pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    int visibleIndex = position - getFirstVisiblePosition();
                    willFlingView = (SlidingRemovedItemToLeft) getChildAt(visibleIndex);
                    doDelete(willFlingView);
                }
                //恢复已经滑动的item
                restoreListItems();
                break;
            case MotionEvent.ACTION_MOVE:
                //标记是否满足滑动条件
                float xVelocity = velocityTracker.getXVelocity();
                if ((Math.abs(xVelocity) > SNAP_VELOCITY
                        || Math.abs(x - firstX) > touchSlop
                        && Math.abs(y - firstY) < touchSlop))
                    flags |= SLIDE_MASK;
                break;
            case MotionEvent.ACTION_UP:
                //恢复已经滑动的item
                releaseVelocity();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if ((flags & SLIDE_MASK) == SLIDE_MASK
                && position != INVALID_POSITION
                && willFlingView != null) {
            float x = (int) ev.getX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = preX - x;
                    willFlingView.scrollBy((int) dx, 0);
                    preX = x;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    int deleteButtonWidth = willFlingView.getDeleteButton().getWidth();
                    //判断是否向右滑动
                    if (x < firstX && Math.abs(x - firstX) >= deleteButtonWidth * 0.8) {
                        forwardToLeft();
                    } else if (x > firstX || Math.abs(x - firstX) < deleteButtonWidth * 0.8) {
                        rollbackToRight();
                    }
                    postInvalidate();
                    flags &= ~SLIDE_MASK;//左右滑动已取消
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            willFlingView.scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    public void setOnItemRemovedListener(
            OnItemRemovedListener onItemRemovedListener) {
        this.onItemRemovedListener = onItemRemovedListener;
    }

    private void obtainVelocity(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void releaseVelocity() {
        if (velocityTracker != null) {
            velocityTracker.clear();
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * 继续向左滑动
     */
    private void forwardToLeft() {
        int scrollX = willFlingView.getScrollX();
        int deleteButtonWidth = willFlingView.getDeleteButton().getWidth();
        int remain = deleteButtonWidth - scrollX;
        //继续滑动
        scroller.startScroll(scrollX, 0, remain, 0, Math.abs(remain));
        //设置删除按钮滑出标识
        flags |= DELETE_VISIBLE_MASK;
    }

    /**
     * 向右回退
     */
    private void rollbackToRight() {
        int scrollX = willFlingView.getScrollX();
        int deleteButtonWidth = willFlingView.getDeleteButton().getWidth();
        int remain = deleteButtonWidth + scrollX;
        scroller.startScroll(scrollX, 0, -remain, 0, Math.abs(remain));
        //取消删除按钮滑出标识
        flags &= ~DELETE_VISIBLE_MASK;
    }

    /**
     * 恢复所有列表项的状态
     */
    private void restoreListItems() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof SlidingRemovedItemToLeft) {
                SlidingRemovedItemToLeft childLayout = (SlidingRemovedItemToLeft) child;
                if (willFlingView != childLayout) {
                    int deleteButtonWidth = willFlingView.getDeleteButton().getWidth();
                    childLayout.scrollTo(-deleteButtonWidth, 0);
                }
            }
        }
    }

    /**
     * 执行删除
     *
     * @param slidingRemovedItemToLeft
     */
    private void doDelete(SlidingRemovedItemToLeft slidingRemovedItemToLeft) {
        View deleteButton = slidingRemovedItemToLeft.getDeleteButton();
        if (deleteButton == null) return;
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemRemovedListener != null) {
                    //回调
                    onItemRemovedListener.itemRemoved(position, getAdapter());
                    rollbackToRight();
                }
            }
        });
    }

    /**
     * 删除列表项的回调接口
     */
    public interface OnItemRemovedListener {
        /**
         * 删除列表项
         *
         * @param position 删除的列表项索引
         * @param adapter  适配器
         */
        void itemRemoved(int position, ListAdapter adapter);
    }
}
