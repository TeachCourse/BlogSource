package cn.teachcourse.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.teachcourse.R;
import cn.teachcourse.utils.SharedPreferenceUtil;

import static cn.teachcourse.R.id.input_content_et;

public class SharedPreferencesActivity extends AppCompatActivity {
    boolean isHasData = false;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);
        preferences = getSharedPreferences("cn.teachcourse.demo", Context.MODE_PRIVATE);
        editor = preferences.edit();
        initView();
    }

    private void initView() {
        final EditText et = (EditText) findViewById(input_content_et);
        final TextView tv = (TextView) findViewById(R.id.display_content_tv);
        final Button btn = (Button) findViewById(R.id.save_to_xml_btn);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = "{\"tokenId\":\"0bf933de7712c412f1697de886da8e7e97797c40\",\"id\":2431,\"nickName\":\"钊林IT\",\"realName\":\"\",\"phone\":\"18278611095\",\"sex\":\"M\",\"address\":\"中国广东省广州市越秀区沿江中路195号\",\"longitude\":\"113.276545\",\"latitude\":\"23.121409\",\"idCard\":\"\",\"integral\":43,\"imgPath\":\"\\/userfiles\\/userInfo\\/2431\\/1487575421167.jpg\",\"userType\":\"2\",\"integralCard\":\"801506160594\",\"workStatus\":\"2\",\"deviceToken\":\"Ams205FzRA1dzRIISNrwN4Mb7AEWyLZCwgxFH7jAE-FS\",\"inviteCode\":\"MZni2q\",\"undoneOrderNum\":0,\"notEvaluateNum\":0,\"successOrderNum\":0,\"cancelList\":[{\"id\":94,\"label\":\"下错单\",\"value\":\"1\",\"type\":\"cancel_reason\",\"desciption\":\"取消原因\",\"sort\":1},{\"id\":95,\"label\":\"协商取消\",\"value\":\"2\",\"type\":\"cancel_reason\",\"desciption\":\"取消原因\",\"sort\":2},{\"id\":96,\"label\":\"无回收人员上门\",\"value\":\"3\",\"type\":\"cancel_reason\",\"desciption\":\"取消原因\",\"sort\":3}]}";
                if (!TextUtils.isEmpty(content) && !isHasData) {
                    isHasData = true;
                    btn.setText("读出数据");
//            editor.putString("content",content).commit();
                    SharedPreferenceUtil.initPreference(SharedPreferencesActivity.this);
                    SharedPreferenceUtil.putString("content", content);
                    et.setText("");
                } else {
//            content=preferences.getString("content","");
                    content = SharedPreferenceUtil.getString("content", "");
                    if (!TextUtils.isEmpty(content) && isHasData) {
                        isHasData = false;
                        tv.setText(content);
//                editor.remove("content").commit();
                        SharedPreferenceUtil.remove("content");
                        btn.setText("保存数据");
                    } else {
                        Toast.makeText(SharedPreferencesActivity.this, "先输入要保存内容", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void systemMethod(String content, Button btn, EditText et, TextView tv) {
        if (!TextUtils.isEmpty(content) && !isHasData) {
            isHasData = true;
            btn.setText("读出数据");
//            editor.putString("content",content).commit();
            SharedPreferenceUtil.putString("content", content);
            et.setText("");
        } else {
//            content=preferences.getString("content","");
            content = SharedPreferenceUtil.getString("content", "");
            if (!TextUtils.isEmpty(content) && isHasData) {
                isHasData = false;
                tv.setText(content);
//                editor.remove("content").commit();
                SharedPreferenceUtil.remove("content");
                btn.setText("保存数据");
            } else {
                Toast.makeText(SharedPreferencesActivity.this, "先输入要保存内容", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
