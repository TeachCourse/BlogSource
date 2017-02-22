package cn.teachcourse.view.recyclerview.netease;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.teachcourse.R;

public class TabsActivity extends AppCompatActivity {
    private static final String TAG=TabsActivity.class.getName();
    private static final String toItems[]={"头条","财经","热点","政务","直播","社会","娱乐","健康","历史","军事"};
    private static final String fromItems[]={"博客","彩票","段子","轻松一刻","房产","论坛","时尚","体育","移动互联"};
    private RecyclerView fromRecyclerView,toRecyclerView;
    List<String> mList=new ArrayList<String>();
    List<String> list=new ArrayList<String>();
    private RecyclerAdapter mAdapter;
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent(context,TabsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(intent);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        initView();
    }
    /**
     * 初始化布局控件
     */
    private void initView(){

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        fromRecyclerView=(RecyclerView)findViewById(R.id.from_recyclerview);
        fromRecyclerView.setLayoutManager(gridLayoutManager);
        for (String item:fromItems){
            mList.add(item);
        }
        fromRecyclerView.setAdapter(new RecyclerAdapter(mList));
        fromRecyclerView.addItemDecoration(itemDecoration);
        itemTouchHelper.attachToRecyclerView(fromRecyclerView);

        GridLayoutManager gridLayoutManager2=new GridLayoutManager(this,4);
        toRecyclerView=(RecyclerView)findViewById(R.id.to_recyclerview);
        toRecyclerView.setLayoutManager(gridLayoutManager2);
        for (String item:toItems){
            list.add(item);
        }
        mAdapter=new RecyclerAdapter(list);
        toRecyclerView.setAdapter(mAdapter);
        toRecyclerView.addItemDecoration(itemDecoration);
        itemTouchHelper.attachToRecyclerView(toRecyclerView);

    }
    /**
     * 重写RecyclerView.ItemDecoration
     */
    private RecyclerView.ItemDecoration itemDecoration=new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left=15;
            outRect.top=15;
            outRect.right=15;
            outRect.bottom=15;
        }
    };
    /**
     * 创建RecyclerView.ViewHolder
     */
    private class OnItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public OnItemViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.text);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_round_corner));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(2,2,2,2);
        }
    }
    /**
     * 创建绑定数据的RecyclerView.Adapter
     */
    private class RecyclerAdapter extends RecyclerView.Adapter<OnItemViewHolder>{
        private List<String> list;

        public RecyclerAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public OnItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_text,parent,false);

            return new OnItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(OnItemViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {

            return list.size();
        }
    }
    /**
     * 创建ItemTouchHelper对象
     */
    private ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            //设置侧滑方向为从左到右和从右到左都可以
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            //将方向参数设置进去
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            //如果两个item不是一个类型的，我们让他不可以拖拽
            if (viewHolder.getItemViewType() != target.getItemViewType()) {
                return false;
            }
            int fromPostion=viewHolder.getAdapterPosition();
            int toPosition=target.getAdapterPosition();
            if(fromPostion<toPosition){
                for (int i=fromPostion;i<toPosition;i++){
                    Collections.swap(list,i,i+1);
                }
            }else{
                for (int i=fromPostion;i>toPosition;i--){
                    Collections.swap(list,i,i-1);
                }
            }
            mAdapter.notifyDataSetChanged();
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            list.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    });
}
