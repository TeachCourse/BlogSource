package cn.teachcourse.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.utils.DensityUtil;

/**
 * Created by lvzhiqing on 2016/8/18.
 * 首页的轮播图。
 */
public class SecondDetailFragmentViewPagerLunBoAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private final ViewPager headViewPager;
    private final Context mActivity;
    private final LinearLayout linearLayout;
    private LinearLayout.LayoutParams params;
    private int position;
    private List<ImageView> listPoint;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SCROLL_WHAT) {
                //自动滚动。
                headViewPager.setCurrentItem(headViewPager.getCurrentItem() + 1);

                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(SCROLL_WHAT, 3000);
            }
        }
    };

    public int[] images = new int[]{//
            R.drawable.background_one, R.drawable.background_one, R.drawable.background_one,//
            R.drawable.background_one, R.drawable.background_one};
    //private List<CarouselPictureInfo.DataBean> dataBeen;
    private final List<ImageView> listImage;
    private int pagerCount= 100000;

    /**
     *viewPager开始轮播，
     */
    public void carouselViewPager() {

        if (handler != null) {
            handler.sendEmptyMessageDelayed(SCROLL_WHAT, 3000);

        }

    }

    private final int SCROLL_WHAT = 0;
    private boolean isDraggeing = false;//不是用户拖动状态。

    public SecondDetailFragmentViewPagerLunBoAdapter(ViewPager headViewPager, Context mActivity, LinearLayout headView_ll_point) {
        //dataBeen = carouselPictureData.getData();
        this.headViewPager = headViewPager;

        this.mActivity = mActivity;

        headViewPager.addOnPageChangeListener(this);//设置页面改变的监听。

        linearLayout = headView_ll_point;
        listPoint = new ArrayList<>();


        for (int i = 0; i < 5; i++) {//添加小圆点到指定视图。
            ImageView imageView = new ImageView(mActivity);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = DensityUtil.dip2px(mActivity,5);
            params.leftMargin =DensityUtil.dip2px(mActivity,5);
            imageView.setImageResource(R.drawable.dot_normal);
            imageView.setLayoutParams(params);
            listPoint.add(imageView);//添加到集合中。
            headView_ll_point.addView(imageView);//添加控件中。;
        }

        //根据轮播图的个数创建对应的控件个数。
        listImage = new ArrayList();
        for(int i = 0; i<5; i++) {
            ImageView imageView = new ImageView(mActivity);
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            imageView.setLayoutParams(layoutParams);
            //添加到集合中。
            listImage.add(imageView);
        }


        //设置第一个默认圆点。
        listPoint.get(position).setImageResource(R.drawable.dot_focused);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, 3000);
    }

    @Override
    public int getCount() {

        return pagerCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        /*int pos = position % image.length;//计算出真实下标。*/
        int pos = position % 5;//计算出真实下标。
        ImageView imageView = listImage.get(pos);
        //设置图片绑定控件中。
        //Glide.with(mActivity).load(dataBeen.get(pos).getCover_id()).into(imageView);

        imageView.setImageResource(images[pos]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        return imageView;
    }

    //viewPager的监听。
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        int pos = position % 5;
        listPoint.get(pos).setImageResource(R.drawable.dot_focused);//当前position设置图片。

        if (pos != this.position) {
            ImageView imageView1 = (ImageView) linearLayout.getChildAt(this.position);
            imageView1.setImageResource(R.drawable.dot_normal);
        }
        this.position = pos;
    }


    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == ViewPager.SCROLL_STATE_IDLE && isDraggeing) {//此时发送消息。
            handler.sendEmptyMessageDelayed(SCROLL_WHAT, 3000);
            isDraggeing = false;

        }

        if (state == ViewPager.SCROLL_STATE_DRAGGING) {//拖动状态。
            //handler.removeCallbacksAndMessages(null);//移除消息。
            handler.removeMessages(SCROLL_WHAT);
            isDraggeing = true;
        }
    }

    //获取viewPager中handler实例。
    public Handler getPagerAdapterInsideHandler() {
        return handler;
    }

    //停止轮播图滚动。
    public void removeViewPagerScroll() {
        handler.removeMessages(SCROLL_WHAT);
    }

//    public void setData(CarouselPictureInfo result) {
//        this.dataBeen = result.getData();
//    }
}
