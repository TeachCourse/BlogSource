package cn.teachcourse.urlconnection;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.packages.Downloader;

public class URLConneActivity extends BaseActivity {
    private TextView mTextView;
    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, URLConneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlconne);
        initView();
        initData();
    }
    /**
     * 初始化布局控件
     */
    private void initView(){
        initCommon(getWindow().getDecorView());
        mTextView=(TextView)findViewById(R.id.url_file_length_tv);
    }
    /**
     * 初始化数据
     */

    private void initData(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    int count=Downloader.getInputStream(new URL("http://teachcourse.cn")).getContentLength();
                    Message msg=new Message();
                    msg.arg1=count;
                    mHandler.sendEmptyMessage(0x110);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 刷新UI界面
     */
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what==0x110){
                mTextView.setText("文件大小："+msg.obj+" "+msg.arg1);
            }
        }
    };

    @Override
    public String getUrl() {
        return null;
    }
}
