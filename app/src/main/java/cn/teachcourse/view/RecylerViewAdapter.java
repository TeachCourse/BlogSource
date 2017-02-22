package cn.teachcourse.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/18.
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Integer> list;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public List<Integer> getList() {
        return list;
    }

    public RecylerViewAdapter() {
        list = new ArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
    }

    // RecyclerView的count设置为数据总条数+ 1（footerView）
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).textView.setText(String.valueOf(list
                    .get(position)));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_text, null);
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }
        // type == TYPE_FOOTER 返回footerView
        else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footerview, null);
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }

        return null;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
        }
    }
}
