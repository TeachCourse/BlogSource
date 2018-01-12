package cn.teachcourse.service.music;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;


/**
 * Created by Administrator on 2016/4/25.
 */

public class MusicPlayerActivity extends BaseActivity implements OnClickListener{
    ImageButton start;//播放、暂停按钮
    ImageButton stop;//停止按钮
    ActivityReceiver activityReceiver;
    int status = 1;//当前的状态,1没有声音播放 ,2 正在播放声音,3暂停
    ListView lv;

    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context,MusicPlayerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {       //重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_service);                      //设置当前的用户界面
        initButton(getWindow().getDecorView());
        lv = (ListView)findViewById(R.id.singtext); 		//获得ListView对象的引用
        BaseAdapter myAdapter=new BaseAdapter(){//为ListView准备内容适配器
            //定义歌词字符串的数组
            String[] singtxts=getResources().getStringArray(R.array.singtexts);

            public int getCount() {return singtxts.length;}  //歌词字符串的数组的总条目数，这里共23条
            public Object getItem(int arg0) { return null; }
            public long getItemId(int arg0) { return 0; }
            public View getView(int arg0, View arg1, ViewGroup arg2) {
                //动态生成每个下拉项对应的View，每个下拉项View由LinearLayout
                //中包含一个TextView构成
                LinearLayout ll=new LinearLayout(MusicPlayerActivity.this);
                //初始化LinearLayout
                ll.setOrientation(LinearLayout.HORIZONTAL);		    //设置朝向
                ll.setPadding(3,0,0,0);                             //设置列表框的四周留白
                TextView tv=new TextView(MusicPlayerActivity.this); //初始化TextView
                tv.setText(singtxts[arg0].toString()); //设置内容
                tv.setTextSize(15);                                 //设置字体大小
                tv.setTextColor(MusicPlayerActivity.this.getResources()
                        .getColor(R.color.color_LightGrey));           //设置字体颜色
                tv.setPadding(3,0,0,0);                             //设置文本控件的四周留白
                tv.setGravity(Gravity.LEFT);                        //设置文字居左对齐
                ll.addView(tv);                                     //添加到LinearLayout中
                return ll;
            }
        };
        lv.setAdapter(myAdapter);
        lv.setVisibility(View.INVISIBLE);

        start = (ImageButton) this.findViewById(R.id.start);//得到start的引用
        stop = (ImageButton) this.findViewById(R.id.stop);  //得到stop按钮的引用
        start.setOnClickListener(this);                     //为按钮添加监听
        stop.setOnClickListener(this);                      //为按钮添加监听
        activityReceiver = new ActivityReceiver();          //创建BroadcastReceiver
        IntentFilter filter = new IntentFilter();           //创建IntentFilter过滤器
        filter.addAction("cn.com.sgmsc.MusicPlayer.update");//添加Action
        registerReceiver(activityReceiver, filter);         //注册监听
        Intent intent = new Intent(this, MusicService.class);  //创建Intent
        startService(intent);                               //启动后台Service
    }

    @Override
    public String getUrl() {
        return null;
    }

    /*自定义的BroadcastReceiver*/
    public class ActivityReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {//重写的onReceive方法
            int mupdate = intent.getIntExtra("musicupdate", -1);//得到intent中的数据
            switch(mupdate){        //分支判断
                case 1:                //没有声音播放
                    start.setImageResource(R.drawable.png1);//更换图片
                    status = 1;        //设置当前状态
                    break;
                case 2:                //正在播放声音
                    start.setImageResource(R.drawable.png3);//更换图片
                    status = 2;       //设置当前状态
                    break;
                case 3:               //暂停中
                    start.setImageResource(R.drawable.png2);//更换图片
                    status = 3;       //设置当前状态
                    break;
            }
        }
    }

    /*接口中的方法*/
    @Override
    public void onClick(View v) {
        Intent intent = new Intent("cn.com.sgmsc.MusicPlayer.control");//创建Intent
        switch(v.getId()){               //分支判断
            case R.id.start:                 //按下播放、暂停按钮
                lv.setVisibility(View.VISIBLE);
                intent.putExtra("ACTION", 1);//存放数据
                sendBroadcast(intent);       //发送广播
                break;
            case R.id.stop:                  //按下停止按钮
                intent.putExtra("ACTION", 2);//存放数据
                sendBroadcast(intent);       //发送广播
                break;
        }
    }

    @Override
    protected void onDestroy() {//释放时被调用
        super.onDestroy();
        Intent intent = new Intent(this, MusicService.class);//创建Intent
        stopService(intent);//停止后台的Service
    }

    /*退出菜单及对话框*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){//弹出菜单
        menu.add(0,Menu.FIRST,0,"退出")
                .setIcon(android.R.drawable.ic_menu_delete);//设置图标
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){//选择的菜单项
        switch(item.getItemId()){//分支判断
            case Menu.FIRST:
                showDialog(1);//显示对话框
                break;
        }
        //将来可在此进行扩展
        return false;
    }
    @Override
    protected Dialog onCreateDialog(int id){//创建对话框
        switch(id){//判断
            case 1:
                return new AlertDialog.Builder(this)
                        .setTitle("您确定退出？")
                        .setPositiveButton("确定", new DialogInterface
                                .OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);//直接退出
                            }
                        })
                        .setNegativeButton("取消", null)//取消按钮
                        .create();
            default:
                return null;
        }
    }

}
