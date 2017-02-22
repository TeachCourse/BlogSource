package cn.teachcourse.view.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import cn.teachcourse.R;

/**
 * Created by postmaster@teachcourse.cn on 2016/6/12.
 */
public class SimpleFragment extends Fragment {
    private static final String TAG = SimpleFragment.class.getName();
    private int mIndicator;//indicator数据
    private TextView mTextView;//展示数据信息

    /**
     * 创建多个Fragment
     * @param position
     * @return
     */
    public static SimpleFragment newInstance(int position) {
        SimpleFragment f = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt("indicator", position);
        f.setArguments(args);
        return f;
    }
    /**
     * onCreate()重写方法获取传递过来的参数
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIndicator=getArguments()==null?1:getArguments().getInt("indicator")+1;
    }
    /**
     * onCreateView()重写方法初始化布局文件
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.list_item_text,container,false);
        mTextView=(TextView) view.findViewById(R.id.text);
        return view;
    }
    /**
     * onActivityCreated()重写方法赋值
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextView.setText("第 "+mIndicator+" 个Fragment");
    }
}
