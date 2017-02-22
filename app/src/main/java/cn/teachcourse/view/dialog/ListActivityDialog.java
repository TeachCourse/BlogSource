package cn.teachcourse.view.dialog;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.adapter.MyBaseAdapter;
import cn.teachcourse.bean.TestDialogBean;
import cn.teachcourse.utils.DensityUtil;
import cn.teachcourse.view.viewholder.MyViewHolder;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public class ListActivityDialog extends ListActivity {
    private static final String TAG = "ListActivityDialog";
    private List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        addEvent();
    }

    private void addEvent() {
        setListAdapter(new ListActivityAdapter());
    }

    private void initData() {
        mList = new ArrayList();
        mList.add(new TestDialogBean(1, "一、不同API版本，Dialog默认样式对比"));
        mList.add(new TestDialogBean("API 19"));
        mList.add(new TestDialogBean("API 20"));
        mList.add(new TestDialogBean("API 21"));
        mList.add(new TestDialogBean("API 22"));
        mList.add(new TestDialogBean("API 23"));
        mList.add(new TestDialogBean("API 24"));
        mList.add(new TestDialogBean(2, "二、相同API版本，不同的Context，Dialog默认样式对比"));
        mList.add(new TestDialogBean("继承Activity", ActivityDialog.class));
        mList.add(new TestDialogBean("继承FragmentActivity",FragmentActivityDialog.class));
        mList.add(new TestDialogBean("继承AppCompatActivity",AppCompatActivityDialog.class));
        mList.add(new TestDialogBean("继承ActionBarActivity",ActionBarDialog.class));
        mList.add(new TestDialogBean("继承ListActivity",ListActivityDialog.class));

    }

    /**
     * 设计弹窗
     *
     * @param bean 弹窗内容
     */
    private View createDialog(TestDialogBean bean) {
        /**LinearLayout默认水平方向布局*/
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.px2dip(this,400)));
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView mTitle_tv = new TextView(this);
        mTitle_tv.setGravity(Gravity.CENTER);
        ll.addView(mTitle_tv);

        ImageView mPic_iv = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
        mPic_iv.setLayoutParams(lp);
        ll.addView(mPic_iv);

        if (bean != null) {
            mTitle_tv.setText(bean.getContent());
            mPic_iv.setImageDrawable(getResources().getDrawable(R.drawable.network_error));
        }
        return ll;
    }

    private class ListActivityAdapter extends MyBaseAdapter {

        @Override
        public List getData() {
            return mList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new MyViewHolder();
                TextView textView = new TextView(ListActivityDialog.this);
                int size= DensityUtil.px2dip(ListActivityDialog.this, 28);
                textView.setPadding(size,size,size,size);
                textView.setGravity(Gravity.CENTER);
                viewHolder.textView = textView;
                convertView = viewHolder.textView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            TestDialogBean bean = (TestDialogBean) mList.get(position);

            if (bean.getType() != 0) {
                viewHolder.textView.setTextSize(DensityUtil.px2dip(ListActivityDialog.this, 28));
                viewHolder.textView.setPadding(0,0,0,0);
                viewHolder.textView.setGravity(Gravity.LEFT);
            }

            viewHolder.textView.setText(bean.getContent());

            return convertView;
        }
    }
}
