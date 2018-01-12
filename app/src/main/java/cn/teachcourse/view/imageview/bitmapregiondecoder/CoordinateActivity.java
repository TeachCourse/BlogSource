package cn.teachcourse.view.imageview.bitmapregiondecoder;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

/**
 * Created by Administrator on 2016/4/26.
 */
public class CoordinateActivity extends BaseActivity {
    private String[] items;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_main);
        items=getResources().getStringArray(R.array.items);
        mListView=(ListView)findViewById(R.id.coordinate_listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemSelected);
    }
    /**
     * 逻辑业务处理
     */
    private void doSomething(int  position){
        switch (position){
            case 0:
                //原点坐标系统的计算
                show("父容器屏幕左上角（0,0）坐标");
                break;
            case 1:
                //关于Scroll
                show("垂直方向滚动的距离："+mListView.getScrollY());
                break;
            case 2:
                show("event.getRowX（）：触摸点相对于屏幕原点的x坐标");
                break;
            case 3:
                show("event.getX（）：触摸点相对于其所在组件原点的x坐标");
                break;
            case 4:
                show("event.getX（）：触摸点相对于其所在组件原点的x坐标");
                break;
        }
    }

    /**
     * 展示提示内容
     * @param info
     */
    private void show(String info){
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }
    /**
     * 根据不同的选项展示对应的解说内容
     */
    private AdapterView.OnItemClickListener mOnItemSelected=new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            doSomething(position);
        }
    };
    /**
     * 适配器
     */
    private BaseAdapter mAdapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //动态生成每个下拉项对应的View，每个下拉项View由LinearLayout
            //中包含一个TextView构成
            LinearLayout ll=new LinearLayout(CoordinateActivity.this);
            //初始化LinearLayout
            ll.setOrientation(LinearLayout.HORIZONTAL);		    //设置朝向
            ll.setPadding(3,0,0,0);                             //设置列表框的四周留白
            TextView tv=new TextView(CoordinateActivity.this); //初始化TextView
            tv.setText(items[position].toString()); //设置内容
            tv.setTextSize(15);                                 //设置字体大小
            tv.setPadding(3,0,0,0);                             //设置文本控件的四周留白
            tv.setGravity(Gravity.LEFT);                        //设置文字居左对齐
            ll.addView(tv);                                     //添加到LinearLayout中
            return ll;
        }
    };

    @Override
    public String getUrl() {
        return null;
    }
}
