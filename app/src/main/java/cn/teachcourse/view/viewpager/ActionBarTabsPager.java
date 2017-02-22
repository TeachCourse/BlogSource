package cn.teachcourse.view.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import cn.teachcourse.R;

/**
 * Created by postmaster@teachcourse.cn on 2016/12/2.
 */

public class ActionBarTabsPager extends AppCompatActivity {
    ViewPager mViewPager;
    TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mViewPager = new ViewPager(this);
//        mViewPager.setId(R.id.pager);
        setContentView(R.layout.activity_actionbar_tabs);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        final ActionBar bar =getSupportActionBar() ;
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        mTabsAdapter = new TabsAdapter(this, mViewPager);
        mTabsAdapter.addTab(bar.newTab().setText("Simple"),
                SimpleFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("List"),
                SimpleFragment.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Cursor"),
                SimpleFragment.class, null);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter
            implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabsAdapter.TabInfo> mTabs = new ArrayList<TabsAdapter.TabInfo>();

        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }
        public TabsAdapter(AppCompatActivity activity, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mActionBar = activity.getSupportActionBar() ;
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.addOnPageChangeListener(this);
        }

        public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
            TabsAdapter.TabInfo info = new TabsAdapter.TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabsAdapter.TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

//        @Override
//        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//            Object tag = tab.getTag();
//            for (int i = 0; i < mTabs.size(); i++) {
//                if (mTabs.get(i) == tag) {
//                    mViewPager.setCurrentItem(i);
//                }
//            }
//        }
//
//        @Override
//        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//        }
//
//        @Override
//        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//        }


        @Override
        public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i = 0; i < mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }
    }
}
