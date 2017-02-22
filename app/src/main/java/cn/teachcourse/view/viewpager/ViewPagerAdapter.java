package cn.teachcourse.view.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by postmaster@teachcourse.cn on 2016/6/12.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    private static List<String> mList;
    public static List<String> getmList() {
        return mList;
    }

    public static void setmList(List<String> mList) {
        ViewPagerAdapter.mList = mList;
    }

    /**
     * 必须重写的构造方法
     * @param fm
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 创建多个Fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return SimpleFragment.newInstance(position);
    }

    /**
     * 返回indicator的数量
     * @return
     */
    @Override
    public int getCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    /**
     * 返回indicator的标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (mList != null) {
            return mList.get(position);
        }
        return null;
    }
}
