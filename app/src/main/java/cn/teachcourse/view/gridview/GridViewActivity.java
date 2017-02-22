package cn.teachcourse.view.gridview;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;

public class GridViewActivity extends AppCompatActivity {
    private static final String TAG=GridViewActivity.class.getName();
    private static final String toItems[]={"头条","财经","热点","政务","直播","社会","娱乐","健康","历史","军事"};
    private static final String fromItems[]={"博客","彩票","段子","轻松一刻","房产","论坛","时尚","体育","移动互联"};
    private GridView fromGridView,toGridView;
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent(context,GridViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(intent);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initView();
    }
    /**
     * 初始化布局控件
     */
    private void initView(){
        fromGridView=(GridView)findViewById(R.id.from_gridview);
        toGridView=(GridView)findViewById(R.id.to_gridview);
        List<String> mList=new ArrayList<String>();
        for (String item:toItems){
            mList.add(item);
        }
        toGridView.setAdapter(new GridAdapter(this,mList));
        List<String> list=new ArrayList<String>();
        for (String item:fromItems){
            list.add(item);
        }
        fromGridView.setAdapter(new GridAdapter(this,list));
    }
    /**
     * 创建绑定数据的GridAdapter
     */
    private class GridAdapter extends BaseAdapter{
        private Context context;
        private List<String> list;

        public GridAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {

            return list.size();
        }

        @Override
        public Object getItem(int position) {
            if(list!=null){
                return list.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if(list!=null){
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView= LayoutInflater.from(context).inflate(R.layout.list_item_text,parent,false);
            }
            TextView textView=(TextView)convertView.findViewById(R.id.text);
            Drawable drawable=getResources().getDrawable(R.drawable.bg_round_corner);
            textView.setBackgroundDrawable(drawable);
            textView.setText(list.get(position));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(2,2,2,2);
            return convertView;
        }
    }
}
