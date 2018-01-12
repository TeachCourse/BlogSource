package cn.teachcourse.handler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class RefreshUIActivity extends BaseActivity {
    private TextView mTextView;//获取消息
    int i=0;
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent(context,RefreshUIActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_ui);
        mTextView=(TextView)findViewById(R.id.show_info);
        startAuto();
        initButton(getWindow().getDecorView());
    }
    private void startAuto(){
        Message message = mHandler.obtainMessage(0);
        mHandler.sendMessageDelayed(message, 1000);
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateImageView();//刷新界面

            Message message = mHandler.obtainMessage(0);
            sendMessageDelayed(message, 1000);
        }
    };

    private void updateImageView() {
        i++;
        mTextView.setText(""+i);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
