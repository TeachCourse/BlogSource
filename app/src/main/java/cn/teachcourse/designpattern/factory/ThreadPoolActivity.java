package cn.teachcourse.designpattern.factory;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.teachcourse.R;

public class ThreadPoolActivity extends AppCompatActivity {
    private TextView textView;
    private Button launchThread_btn;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        initView();
        /**批量生产童装*/
        Product product=SweaterFactory.createProduct(ChildrenSweater.class);
        product.makeSweater();
        /**批量生产成人装*/
        product=SweaterFactory.createProduct(AdultSweater.class);
        product.makeSweater();
        /**批量生产老年装*/
        product=SweaterFactory.createProduct(OldSweater.class);
        product.makeSweater();

        /**第二种方式：*/
        product=new ChildrenSweater();

    }

    private void initView() {
        textView= (TextView) findViewById(R.id.display_thread_name_tv);
        launchThread_btn= (Button) findViewById(R.id.launch_thread_btn);
    }

    public void launchThread(View view){
        DefaultConfigurationFactory.createTaskDistributor().execute(new LoadTask(textView,handler));
    }

}
