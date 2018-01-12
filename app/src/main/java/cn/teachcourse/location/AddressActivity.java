package cn.teachcourse.location;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;

public class AddressActivity extends BaseActivity {
    private static final String TAG=AddressActivity.class.getName();
    private TextView mTextView;//展示Address类使用
    public static void start(Context context){
        start(context,null);
    }
    public static void start(Context context, Bundle extras){
        Intent intent=new Intent(context,AddressActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if(extras!=null){
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        String info = "1992.05.06:123456";
        String splitStr = info.split(":")[0];
        mTextView.setText(splitStr);
    }
    /**
     * 初始化布局控件
     */
    private void initView(){
        initButton(getWindow().getDecorView());
        mTextView=(TextView)findViewById(R.id.address_des_tv);
        Address address=getAddress();
        address.setCountryName("中国");
        address.setCountryCode("0881");
        String country="国家："+address.getCountryName()+"\n";
        String code="编码："+address.getCountryCode()+"\n";
        mTextView.setText(country+code);

    }
    /**
     * 创建一个Address对象
     */
    private Address getAddress(){

        return new Address(new Locale(Locale.CHINA.getLanguage()));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
