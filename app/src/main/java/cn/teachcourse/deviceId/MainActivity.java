package cn.teachcourse.deviceId;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class MainActivity extends BaseActivity {
    private TextView mTextView;

    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context,Intent extras){
        Intent intent=new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceid_main);
        initView();
        initData();
    }

    private void initView() {
        initButton(getWindow().getDecorView());
        mTextView = (TextView) findViewById(R.id.dev_id);
    }

    private void initData() {
        UniqueDevId mUniqueId = new UniqueDevId(this);

        mTextView.setText("ANDROID_ID："+mUniqueId.getDevId(UniqueDevId.ANDROID_ID) +
                "\nDEVICE_ID：" + mUniqueId.getDevId(UniqueDevId.DEVICE_ID) +
                "\nINSTALLTION_ID：" + mUniqueId.getDevId(UniqueDevId.INSTALLTION_ID) +
                "\nPSEUDO_UNIQUE_ID：" + mUniqueId.getDevId(UniqueDevId.PSEUDO_UNIQUE_ID)+
                "\nSERIAL_NUMBER_ID："+mUniqueId.getDevId(UniqueDevId.SERIAL_NUMBER_ID)+
                "\nMAC_ADDRESS_ID："+mUniqueId.getDevId(UniqueDevId.MAC_ADDRESS_ID));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
