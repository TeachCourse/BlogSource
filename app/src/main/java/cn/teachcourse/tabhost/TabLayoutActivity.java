package cn.teachcourse.tabhost;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseFragment;


public class TabLayoutActivity extends AppCompatActivity {
    private static final String TAG = "TabLayoutActivity";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<BaseFragment> fragmentList;
    private List<String> titleList;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();
        initData();
        addEvent();
    }

    /**
     * 初始化事件
     */
    private void addEvent() {
//        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mAdapter = new ViewPagerAdapter2(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        //关联 viewpager 和 tabLayout。后面的 true 是是否自动刷新 fragment 的布尔值
        mTabLayout.setupWithViewPager(mViewPager,true);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        titleList.add("推荐");
        titleList.add("新闻");
        titleList.add("视频");
        titleList.add("小说");
        for (String title : titleList) {
            fragmentList.add(BaseFragment.newInstance(title));
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

    }

    protected static class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> fragmentList;
        private List<String> titleList;

        public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    private class ViewPagerAdapter2 extends FragmentStatePagerAdapter{

        public ViewPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return BaseFragment.newInstance(titleList.get(i));
        }

        @Override
        public int getCount() {
            return titleList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
