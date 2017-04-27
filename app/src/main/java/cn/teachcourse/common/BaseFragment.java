package cn.teachcourse.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by postmaster@teachcourse.cn on 2017/3/14.
 */

public class BaseFragment extends Fragment {
    private String  mType;

    public BaseFragment() {
        super();
    }

    public static BaseFragment newInstance(String type) {
        BaseFragment fragment = new BaseFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mType=getArguments().getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView=new TextView(getContext());
        textView.setText(mType);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
