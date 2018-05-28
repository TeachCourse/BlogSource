package cn.teachcourse.screenadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SimpleLayoutActivity extends AppCompatActivity {

    public static void start(Context context){
        Intent intent=new Intent(context,SimpleLayoutActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_layout);
    }
}
