package cn.teachcourse.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 * 简单地封装BaseAdapter，重写{@link CommonAdapter#getLayoutId}和
 * 重写{@link CommonAdapter#onBindViewHolder}
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected  LayoutInflater inflater;
    protected Context context;
    protected List<T> list;
    protected ViewHolder viewHolder;

    public abstract int getLayoutId();

    public abstract void onBindViewHolder(ViewHolder viewHolder,int position);

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list=list;
        inflater= LayoutInflater.from( this.context);
    }

    @Override
    public int getCount() {
            return list.size();
    }

    @Override
    public Object getItem(int position) {
            return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(getLayoutId(),parent,false);
            viewHolder=new ViewHolder(convertView) ;
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder,position);
        return convertView;
    }

    protected static class ViewHolder{
        protected View view;
        public ViewHolder(View view) {
            this.view = view;
        }
        public View findViewById(@IdRes int id){
            return view.findViewById(id);
        }
    }
}
