package cn.teachcourse.json;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.teachcourse.json.bean.DistrictBean;
import cn.teachcourse.support.v4.viewholder.MyViewHolder;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/11.
 */
public class DistrictBaseAdapter extends BaseAdapter {
    private List<DistrictBean> mList;//地区列表
    private Context context;//上下文

    public DistrictBaseAdapter(List<DistrictBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mList!=null){
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mList!=null){
            return  mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mList!=null){
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if(convertView!=null){
            viewHolder=(MyViewHolder)convertView.getTag();
        }else{
            viewHolder=new MyViewHolder();
            TextView view=new TextView(context);
            convertView=view;
            viewHolder.textView=view;
            convertView.setTag(viewHolder);
        }
        viewHolder.textView.setText(mList.get(position).getName());
        return convertView;
    }
}
