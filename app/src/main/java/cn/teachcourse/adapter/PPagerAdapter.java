package cn.teachcourse.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by http://teachcourse.cn on 2017/12/18.
 */

public abstract class PPagerAdapter<T> extends PagerAdapter {
    protected Context mContext;
    protected List<T> mDataSet;
    protected LayoutInflater mInflater;
    private int mChildCount = 0;
    private RefreshType type = RefreshType.NORMAL;
    /*创建SparseArray的目的，存储每个ViewPager对应位置上第一次初始化的View，在ViewPager子控件很多的时候，这种方法优化滑动效果，更加流畅*/
    private SparseArray<View> mViewSparseArray;

    public enum RefreshType {
        REMOVE, NORMAL
    }

    public PPagerAdapter(Context mContext, List<T> mDataSet) {
        this.mContext = mContext;
        this.mDataSet = mDataSet;
        mViewSparseArray = new SparseArray<>(mDataSet.size());
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewSparseArray.get(position);
        if (view == null) {
            view = (View) initItem(container, position);
            mViewSparseArray.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewSparseArray.get(position));
    }

    public abstract Object initItem(ViewGroup container, int position);

    public void notifyDataSetChanged(RefreshType type) {
        this.type = type;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (type == RefreshType.REMOVE) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
