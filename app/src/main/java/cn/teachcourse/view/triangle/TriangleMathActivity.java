package cn.teachcourse.view.triangle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.teachcourse.R;

public class TriangleMathActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "TriangleMathActivity";
    /**
     * 计算任何一个角度正弦、余弦和正切
     */
    public static final double TAN = Math.sin(30);
    /**
     *返回弧度数
     */
    public static final double ASIN = Math.asin(0.5);
    /**
     * 计算角度
     */
    public static final double DEGREE=Math.toDegrees(ASIN);
    private EditText mInputValue_et;
    private TextView mOutputValue_tv;
    private StringBuffer sb=new StringBuffer();
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle_math);
        initView();
        addEvent();
    }

    private void addEvent() {
        mInputValue_et.addTextChangedListener(this);
    }

    private void initView() {
        mInputValue_et = (EditText) findViewById(R.id.input_angle_et);
        mOutputValue_tv = (TextView) findViewById(R.id.output_value_tv);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /**
         * 检查输入的是否为一个0到360的度数或一个0到1的浮点数
         */
        if (count==0)
            return;


        if (!isNumber(s.subSequence(count-1,count))){
            Log.d(TAG, "onTextChanged: "+s.subSequence(count-1,count));
            Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
            return;
        }

        sb.append(s);
        if (!isAngleValue(Double.valueOf(sb.toString()))){
            Toast.makeText(this, "请输入正确的角度", Toast.LENGTH_SHORT).show();
            return;
        }
        info= "正弦："+TAN +"\n弧度："+ ASIN+"\n角度："+DEGREE+"\nSB:"+sb.toString()+"\nS:"+s.toString();
        mOutputValue_tv.setText(info);
    }

    @Override
    public void afterTextChanged(Editable s) {
           sb.delete(0,sb.length());
    }

    /**
     * 判断是否输入数字
     * @param s
     * @return
     */
    private boolean isNumber(CharSequence s) {
        Pattern p = Pattern.compile("^([0-9])$");
        Matcher m = p.matcher(s.toString());

        return m.matches();
    }

    /**
     * 判断输入的是否0到360的度数
     * @param value
     * @return
     */
    private boolean isAngleValue(double value){
        if (value<0|value>360){

            return false;
        }
        return true;
    }
}
