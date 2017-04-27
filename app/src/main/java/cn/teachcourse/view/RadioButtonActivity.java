package cn.teachcourse.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.chenjing.PullableScrollViewActivity;
import cn.teachcourse.common.BaseActivity;

public class RadioButtonActivity extends BaseActivity {
    private static final String TAG = "RadioButtonActivity";
    private RadioGroup mRadioGroup;
    public static void start(Context context)
    {
        start(context, null);
    }

    public static void start(Context context, Intent extras)
    {
        Intent intent = new Intent();
        intent.setClass(context, RadioButtonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);
        // 为RadioGroup单选按钮组添加监视器
        mRadioGroup=((RadioGroup) findViewById(R.id.radiogroup1));
        mRadioGroup.setOnCheckedChangeListener(radioChange);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        LayoutInflater inflater=getLayoutInflater();
        for (int i=0;i<5;i++){
            RadioButton radioButton= (RadioButton) inflater.inflate(R.layout.radio_button,mRadioGroup,false);
//            RadioButton radioButton=new RadioButton(this);
            radioButton.setId(i);
            radioButton.setText("单选按钮"+i);
            radioButton.setButtonDrawable(null);
            radioButton.setLayoutParams(params);
            mRadioGroup.addView(radioButton);
        }

        // 为提交按钮添加监视器
        ((Button) findViewById(R.id.button1)).setOnClickListener(lick);
        initCommon(getWindow().getDecorView());
    }

    // 创建OnCheckedChangeListener对象并实例化，然后重写onCheckedChanged方法
    final RadioGroup.OnCheckedChangeListener radioChange = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup arg0, int checkedId) {
            // TODO Auto-generated method stub
            RadioButton radioButton = (RadioButton) findViewById(checkedId);
            Log.i("单选按钮组", "您选择的是：" + radioButton.getText());

        }

    };
    // 创建OnClickListener对象并实例化，然后重写onClick()方法
    final View.OnClickListener lick = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            // 通过for循环遍历单选按钮组
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup1);
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                // 调用RadioGroup的getChildAt(int index)方法返回RadioButton对象
                RadioButton radioButton = (RadioButton) radioGroup
                        .getChildAt(i);
                if (radioButton.isChecked())
                    Toast.makeText(RadioButtonActivity.this,
                            "您选择：" + radioButton.getText(), Toast.LENGTH_SHORT).show();

            }
        }

    };

    @Override
    public String getUrl() {
        return null;
    }
}
