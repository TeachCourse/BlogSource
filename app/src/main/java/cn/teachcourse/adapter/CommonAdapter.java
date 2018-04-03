package cn.teachcourse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected  LayoutInflater inflater;
    protected Context context;
    protected List<T> list;
    protected ViewHolder viewHolder;

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

    public abstract int setLayoutId();
    public abstract void onBindViewHolder(ViewHolder viewHolder,int position);
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(setLayoutId(),parent,false);
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
    }
}
