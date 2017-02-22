package cn.teachcourse.tabhost;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import cn.teachcourse.R;
import cn.teachcourse.service.music.MusicPlayerActivity;

public class OneTabActivity extends TabActivity {
    private TabHost mTabHost;//选项卡管理对象
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context,OneTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_tab);
        initView();
    }
    /**
     * 添加选项卡
     */
    private void initView(){
        mTabHost= (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setFocusable(true);//设置当前对象是否获取焦点
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("消息").setContent(new Intent(this,cn.teachcourse.json.ResolveJSONActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("联系人").setContent(new Intent(this,MusicPlayerActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("动态",getResources().getDrawable(R.drawable.ic_launcher)).setContent(new Intent(this,cn.teachcourse.phone.PhoneNameActivity.class)));
    }
    /**
     * 创建选项卡视图:第一种方式
     */
    private TabHost.TabSpec buildTabSpec(String tabName,int id,int viewId){
//        MyViewHolder viewHolder=null;
//        TextView textView=null;
//        if (textView!=null){
//            viewHolder=(MyViewHolder) textView.getTag();
//        }else{
//            textView=new TextView(this);
//            //配置布局参数
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            textView.setLayoutParams(params);
//            //设置文本
//            textView.setText(tabName);
//            //设置TextView图标位置
//            textView.setCompoundDrawablesWithIntrinsicBounds(null,null,null,getResources().getDrawable(id));
//            viewHolder.textView=textView;
//            textView.setTag(viewHolder);
//        }
        return mTabHost.newTabSpec(tabName).setContent(viewId);
    }
    /**
     * 创建选项卡视图:第二种方式
     */
    private TabHost.TabSpec buildTabSpec(String tabName,int id,Intent intent){

        return  mTabHost.newTabSpec(tabName).setContent(intent);
    }
    /**
     * 创建选项卡视图:第三种方式
     */
    private TabHost.TabSpec buildTabSpec(String tabName,int id,TabHost.TabContentFactory factory){

        return mTabHost.newTabSpec(tabName).setContent(factory);
    }
}
