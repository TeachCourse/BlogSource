package cn.teachcourse.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.teachcourse.R;

public class ReflectUsingActivity extends AppCompatActivity {
    private static final String TAG = "ReflectUsingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect_useing);

        //反射获取cn.teachcourse.MainActivity实例并调用其getUrl()方法、ACTION字段
        try {
            Context mmsCtx = createPackageContext("cn.teachcourse",
                    Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            Class<?> clazz = Class.forName("cn.teachcourse.MainActivity", true, mmsCtx.getClassLoader());
            printField(clazz,"ACTION");
            printMethod(clazz,"getUrl");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void printField(Class<?> clazz,String name){
        try {
            Object instance=clazz.newInstance();
            Field field=clazz.getDeclaredField(name);
            field.setAccessible(true);

            String tag= (String) field.get(instance);
            final TextView textView= (TextView) findViewById(R.id.show_tag_tv);
            textView.setText("I am one TAG of MainActivity："+tag);
            Log.d(TAG,"------------>MainActivity.TAG="+tag);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    private void printMethod(Class<?> clazz,String name){
        try {
            Object instance=clazz.newInstance();
            Class<?>[] classes=new Class[]{};
            Method method=clazz.getDeclaredMethod(name,classes);
            method.setAccessible(true);

            Object[] params=new Object[]{};
            String value= (String) method.invoke(instance,params);
            final TextView textView= (TextView) findViewById(R.id.show_method_value_tv);
            textView.setText("I am one method of MainActivity：getUrl() "+value);
            Log.d(TAG,"------------>MainActivity.TAG="+value);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
