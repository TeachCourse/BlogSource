package cn.teachcourse.view.viewpager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class TabIndicatorActivity extends BaseActivity {
    private static final String TAG=TabIndicatorActivity.class.getName();
    private PagerSlidingTabStrip mTabs;
    private ViewPager mViewPager;
    private List<String> mList=new ArrayList<String>();
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent(context,TabIndicatorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        initData();
        ViewPagerAdapter.setmList(mList);
        ViewPagerAdapter mAdapater=new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapater);
        mTabs.setViewPager(mViewPager);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mTabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
    }
    /**
     * 初始化布局控件
     */
    private void initView(){
        mTabs=(PagerSlidingTabStrip)findViewById(R.id.translate_pagertabstrip);
        mViewPager=(ViewPager)findViewById(R.id.translate_viewpager);
    }
    /**
     * 初始化数据
     */
    private void initData(){
        mList.add("头条");
        mList.add("推荐");
        mList.add("视图");
        mList.add("娱乐");
        mList.add("体育");
        mList.add("财经");
        mList.add("高考");
        mList.add("科技");
        mList.add("汽车");
    }

    @Override
    public String getUrl() {
        return null;
    }
}
