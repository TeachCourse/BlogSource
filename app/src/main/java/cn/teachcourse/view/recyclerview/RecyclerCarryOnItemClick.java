package cn.teachcourse.view.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TeachCourse.cn on 2016/5/28 11:39.
 */
public class RecyclerCarryOnItemClick extends RecyclerView {
    private OnItemClickListener onItemClickListener;
    private GestureDetectorCompat mGestureDetector;

    public RecyclerCarryOnItemClick(Context context) {
        super(context);
    }

    public RecyclerCarryOnItemClick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public RecyclerCarryOnItemClick(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        addOnItemTouchListener(onItemTouchListener);
    }

    public interface OnItemClickListener {
        void onItemClick(ViewHolder vh,int position);
    }

    @Override
    public void addOnItemTouchListener(OnItemTouchListener listener) {
        super.addOnItemTouchListener(listener);
        init();
    }

    /**
     * 实现RecyclerView.OnItemTouchListener接口
     */
    private OnItemTouchListener onItemTouchListener = new OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }
        //方法一：在RecyclerView本身或其子视图处理触摸事件之前，该方法静静监视和接管发送到RecyclerView的事件消息，即对RecyclerView进行事件拦截

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
        }

        //方法二：在方法一返回true，作为手势事件处理的一部分，即对RecyclerView进行触摸事件处理，获取MotionEvent对象，这一步就可以使用GestureDetectorCompat进行事件分发处理（下面详细说明）
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
        //方法三：当RecyclerView子视图不想被RecyclerView或其父类事件拦截，回调该方法
    };

    private void init() {
        mGestureDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    ViewHolder vh = getChildViewHolder(child);
                    int position = getChildAdapterPosition(child);
                    onItemClickListener.onItemClick(vh, position);
                }
                return true;
            }
        });
    }
}
