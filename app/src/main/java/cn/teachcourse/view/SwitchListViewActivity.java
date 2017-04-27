package cn.teachcourse.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class SwitchListViewActivity extends BaseActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecylerViewAdapter adapter;
    private int lastVisibleItem;
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context,SwitchListViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_list_view);
        initView();
    }
    /**
     *  初始化布局控件
     */
    private void initView(){
        initCommon(getWindow().getDecorView());
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_list);
        mLayoutManager = new LinearLayoutManager(this);
        set();
    }
    /**
     * 添加控件的各种set方法
     */
    private void set(){
        mSwipeRefreshLayout.setColorScheme(new int[]{R.color.color_LightGrey,R.color.color_Crimson,R.color.color_LightPink,R.color.color_Pink});
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(refreshLayout);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(onScrollListener);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecylerViewAdapter();
        mRecyclerView.setAdapter(adapter);
    }
    /**
     * 刷新UI界面
     */
    private static final int REFRESH_UI=0X110;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH_UI:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    /**
     * 实现接口OnRefreshListener
     */
    private SwipeRefreshLayout.OnRefreshListener refreshLayout=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeRefreshLayout.setRefreshing(true);
            mHandler.sendEmptyMessageDelayed(REFRESH_UI,1000);
        }
    };
    /**
     * 实现接口OnScrollListener
     */
    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()) {

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    public String getUrl() {
        return null;
    }
}
