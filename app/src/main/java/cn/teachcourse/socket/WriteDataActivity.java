package cn.teachcourse.socket;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class WriteDataActivity extends BaseActivity implements View.OnClickListener {
    private EditText mInputContent_et;
    private Button mSendContent_btn;
    private ConnetionClientThread connectionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_data);
        initView();
        initData();
        addEvent();
    }

    private void initData() {
        connectionClient=new ConnetionClientThread();
        connectionClient.setIp("192.168.11.121");
        connectionClient.setPort(8080);
    }

    private void addEvent() {
        mSendContent_btn.setOnClickListener(this);
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        mInputContent_et = (EditText) findViewById(R.id.send_content_et);
        mSendContent_btn = (Button) findViewById(R.id.send_content_btn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_content_btn:
                sendContent();
                break;
        }
    }

    private void sendContent() {
        String content = mInputContent_et.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            try {
                connectionClient.setSendMessage(content);
                connectionClient.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "请输入发送内容", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public String getUrl() {
        return null;
    }
}
