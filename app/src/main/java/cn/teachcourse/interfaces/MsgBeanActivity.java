package cn.teachcourse.interfaces;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.interfaces.util.MessageService;
import cn.teachcourse.service.music.MusicService;

/**
 * 功能：根据消息类型，实现接口doMsg()方法
 */
public class MsgBeanActivity extends AppCompatActivity{
    private static final String TAG=MsgBeanActivity.class.getName();
    private TextView textView;
    private OnMsgChangeListener listener;
    private List<MsgBean> mListBean=null;

    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent =new Intent();
        intent.setClass(context,MsgBeanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_bean);
        initView();

    }
    private void initView(){
        textView=(TextView)findViewById(R.id.msg_type_id);
        MsgData data=new MsgData(this);
        data.setOnMsgChangeListener(new OnMsgChangeListener() {
            @Override
            public void doMsg(List<MsgBean> listBean) {

                textView.setText("收到的消息类型是："+listBean.get(0).getType()+"\n"+"消息内容是："+listBean.get(0).getMessage());
            }
        });
    }
}
