package cn.teachcourse.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.teachcourse.bean.TestDialogBean;
import cn.teahcourse.baseutil.DensityUtil;
import cn.teachcourse.support.v4.viewholder.MyViewHolder;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public class TestDialogAdapter extends MyBaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List mList;

    public TestDialogAdapter(List list, Context context) {
        this.mList = list;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public List getData() {
        return mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyViewHolder();
            TextView textView = new TextView(mContext);
            int size=(int)DensityUtil.px2dip(mContext, 28f);
            textView.setPadding(size,size,size,size);
            textView.setGravity(Gravity.CENTER);
            viewHolder.textView = textView;
            convertView = viewHolder.textView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        TestDialogBean bean = (TestDialogBean) mList.get(position);

        if (bean.getType() != 0) {
            viewHolder.textView.setTextSize(DensityUtil.px2dip(mContext, 28));
            viewHolder.textView.setPadding(0,0,0,0);
            viewHolder.textView.setGravity(Gravity.LEFT);
        }

        viewHolder.textView.setText(bean.getContent());

        return convertView;
    }
}
