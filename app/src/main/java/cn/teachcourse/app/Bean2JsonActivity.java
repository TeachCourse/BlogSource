package cn.teachcourse.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.bean.BeanA;
import cn.teachcourse.bean.BeanB;
import cn.teachcourse.bean.BeanC;
import cn.teachcourse.bean.BeanD;

import static cn.teachcourse.R.id.json_tv;

public class Bean2JsonActivity extends AppCompatActivity {
    private TextView mTextView;
    private List<BeanD> mListC =new ArrayList<BeanD>();
    private List<BeanC> mListB =new ArrayList<BeanC>();
    private List<BeanB> mListA =new ArrayList<BeanB>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean2_json);
        initView();
        initData();
    }

    private void initData() {
        initBeanD();
    }

    private void initBeanA() {
        BeanA beanA=new BeanA("新湾街道",mListA);
        String json= new Gson().toJson(beanA);
        mTextView.setText(json);
    }

    private void initBeanB() {
        BeanB mBeanB=new BeanB("西湾村", mListB);
        mListA.add(mBeanB);
        initBeanA();
    }

    private void initBeanC() {
        BeanC mBeanC=new BeanC("西湾村", mListC);
        mListB.add(mBeanC);
        initBeanB();
    }

    private void initBeanD() {
        BeanD bean=new BeanD("第2小组");
        BeanD bean2=new BeanD("第3小组");
        mListC.add(bean);
        mListC.add(bean2);
        initBeanC();
    }

    private void initView() {
        mTextView= (TextView) findViewById(json_tv);
    }
}
