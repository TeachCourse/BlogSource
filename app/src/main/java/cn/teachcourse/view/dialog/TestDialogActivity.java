package cn.teachcourse.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.adapter.TestDialogAdapter;
import cn.teachcourse.bean.TestDialogBean;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public class TestDialogActivity extends FragmentActivity {
    private static final String TAG = "TestDialogActivity";
    private ListView mListView;
    private List mList;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_listview);
        initView();
        initData();
        addEvent();
    }

    private void addEvent() {
        mListView.setAdapter(new TestDialogAdapter(mList, this));
        mListView.setOnItemClickListener(mOnItemClickListener);
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

    private void initView() {
        mListView = (ListView) findViewById(R.id.widget_listview);
    }

    /**
     * 设计弹窗
     *
     * @param bean 弹窗内容
     */
    private View createDialog(TestDialogBean bean) {
        /**LinearLayout默认水平方向布局*/
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemClick: \n" + position + "\n" + id);

            TestDialogBean bean = (TestDialogBean) mList.get(position);
            if (bean.getZz() != null)
                startActivity(new Intent(TestDialogActivity.this, bean.getZz()));
            else {
                Dialog dialog = null;
                if (dialog == null)
                    dialog = new Dialog(TestDialogActivity.this,R.style.Theme_CustomDialog);
                dialog.setContentView(createDialog(bean));
                dialog.show();
            }
        }
    };

    private void createAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage("显示AlertDialog对话框内容");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}
