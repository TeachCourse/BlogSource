package cn.teachcourse.view.listview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class ListViewActivity extends BaseActivity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private static final String TAG = ListViewActivity.class.getName();
    private ListView mListView;
    private View mStatuBar;//状态栏
    private ImageView imageView;
    private int height = 0;
    private int y = 0;
    private int scrollaccount;
    private List<String> mList = new ArrayList<String>();
    private static final String[] items = {"getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItem", "getItemId", "getCount", "getView", "getItem", "getItemId", "getCount", "getView", "getItem", "getItemId", "getCount", "getView", "getItem", "getItemId", "getCount", "getView", "getItem", "getItemId", "getCount", "getView", "getItem", "getItemId", "getCount", "getView", "getItem", "getItemId", "getCount", "getView"};
    private GestureDetector mGestureDetector;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, ListViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();
        initData();
        setEvent();
    }

    private void setEvent() {
        mGestureDetector = new GestureDetector(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mOnScroll);
        mListView.setOnTouchListener(this);

    }

    private void initData() {
        for (String item : items)
            mList.add(item);
        imageView.setImageResource(R.drawable.beautiful_girl);
        mListView.addHeaderView(imageView);
    }

    /**
     * 初始化数据
     */
    private void initView() {
        initCommon(getWindow().getDecorView());
        mStatuBar = findViewById(R.id.custom_action_bar);
        mListView = (ListView) findViewById(R.id.widget_listview);
        imageView = new ImageView(this);
    }

    /**
     * 添加适配器
     */
    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public Object getItem(int position) {
            if (mList.size() > 0) {
                return mList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mList.size() > 0) {
                return position;
            }
            return 0;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case firstViewType:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(ListViewActivity.this).inflate(R.layout.list_item_text, parent, false);
                    }
                    TextView textView = (TextView) convertView.findViewById(R.id.text);
                    textView.setText(mList.get(position));
                    break;
                case secondViewType:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(ListViewActivity.this).inflate(R.layout.list_item_text_img, parent, false);
                    }
                    textView = (TextView) convertView.findViewById(R.id.text);
                    textView.setText(mList.get(position));
                    break;
                case threeViewType:
                    imageView = new ImageView(ListViewActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250);
                    imageView.setLayoutParams(params);
                    imageView.setImageResource(R.drawable.background_one);
                    convertView = imageView;
                    break;
            }
            return convertView;
        }

        private int viewTypeCount = 3;
        private static final int firstViewType = 0;//参数必须从0开始，否则报异常：java.lang.ArrayIndexOutOfBoundsException: length=2; index=2
        private static final int secondViewType = 1;
        private static final int threeViewType = 2;

        @Override
        public int getViewTypeCount() {
            return viewTypeCount;
        }

        @Override
        public int getItemViewType(int position) {
            int p = position % 5;
            if (position == 0) {
                return threeViewType;
            } else if (p != 0) {
                return firstViewType;
            }
            return secondViewType;
        }
    };
    /**
     * 滑动监视器
     */
    private boolean isFirst = true;
    private int oldItem = 0;
    private int newItem = 0;
    private AbsListView.OnScrollListener mOnScroll = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//          notifyDataSet(firstVisibleItem, visibleItemCount, totalItemCount);
            if (isFirst) {
                oldItem = firstVisibleItem;
                isFirst = false;
            }
            newItem = firstVisibleItem;
            if (newItem == oldItem) gradientBackground(getScroolY(), imageView.getHeight());

            Log.e(TAG, "onScroll: " + getScroolY() + "top=" + getTop() + ";height=" + height);
        }
    };

    /**
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    private void notifyDataSet(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0) {

        }
        if (visibleItemCount + firstVisibleItem == totalItemCount) {
            String items[] = {"notifyDataSetChanged", "notifyDataSetInvalidate", "getItemViewType", "getItemTypeCount"};
            if (mList.size() > 50) {
                mAdapter.notifyDataSetInvalidated();
                Toast.makeText(ListViewActivity.this, "已调用notifyDataSetInvalidate方法，不再更变items数据", Toast.LENGTH_SHORT);
                return;
            }
            for (String item : items)
                mList.add(item);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void gradientBackground(int y, int height) {

        if (y <= 0) {   //设置标题的背景颜色
            mStatuBar.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            mStatuBar.setBackgroundColor(Color.argb((int) alpha, 110, 217, 108));
        } else {    //滑动到banner下面设置普通颜色
            mStatuBar.setBackgroundColor(Color.argb((int) 255, 110, 217, 108));
        }
    }

    /**
     * 获取上滑的距离
     *
     * @return distance
     */
    public int getScroolY() {
        View c = mListView.getChildAt(0);
        if (null == c) {
            return 0;
        }
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();
/**
 * 声明一下，这里测试得到的top值始终是listview条目中显示的第一条距离顶部的距离，
 * 而这个在坐标中的表示是一个负数，所以需要对其取一个绝对值
 */
        return firstVisiblePosition * c.getHeight() + Math.abs(top);
    }

    /**
     * 获取首条距离顶部的高度
     *
     * @return distance top
     */
    private int getTop() {
        View c = mListView.getChildAt(0);
        if (null == c) {
            return 0;
        }
        return c.getTop();
    }

    /**
     * 解释：Touch down时触发
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 解释：Touch了还没有滑动时触发
     *
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 解释：onSingleTapUp这个函数都是在touch down后又没有滑动（onScroll），又没有长按（onLongPress），然后Touch up时触发
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e(TAG, "onScroll: " + distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 解释：Touch了滑动一点距离后，up时触发。
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e(TAG, "onFling: " + velocityY);
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
