package cn.teachcourse.adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public abstract class MyBaseAdapter extends BaseAdapter {
    /**
     * 获取需要绑定的数据
     * @return
     */
    public abstract List getData();

    @Override
    public int getCount() {
        if (getData()!=null)
            return getData().size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (getData()!=null)
            return getData().get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
