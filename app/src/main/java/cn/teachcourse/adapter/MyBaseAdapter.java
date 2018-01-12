package cn.teachcourse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public abstract class MyBaseAdapter extends BaseAdapter {
    protected LayoutInflater inflater;
    protected Context context;
    protected List list;
    public abstract List getData();
    public MyBaseAdapter() {
    }

    public MyBaseAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list!=null)
            return list.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
