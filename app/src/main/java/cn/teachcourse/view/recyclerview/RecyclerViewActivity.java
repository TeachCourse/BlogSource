package cn.teachcourse.view.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class RecyclerViewActivity extends BaseActivity {
    private static final String TAG = RecyclerViewActivity.class.getName();
    private RecyclerView mRecyclerView;
    private static final String items[] = {"RecyclerView", "RecyclerView.Adapter", "RecyclerView.addOnItemTouchListener"};
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context,RecyclerViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        initButton(getWindow().getDecorView());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 重写RecyclerView.Adapter抽象方法
     */
    private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter() {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_text, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).mTextView.setText(items[position]);
            }
            Log.d(TAG,items[position]);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public ItemViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
            }
        }
    };

    @Override
    public String getUrl() {
        return null;
    }
}
