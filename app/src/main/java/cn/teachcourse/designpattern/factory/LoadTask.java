package cn.teachcourse.designpattern.factory;

import android.os.Handler;
import android.widget.TextView;

/**
 * Created by http://teachcourse.cn on 2017/8/3.
 */

public class LoadTask implements Runnable {
    private TextView textView;
    private Handler handler;

    public LoadTask(TextView textView,Handler handler) {
        this.textView=textView;
        this.handler = handler;
    }

    @Override
    public void run() {
        String threadName=Thread.currentThread().getName();
        DisplayTask displayTask=new DisplayTask(textView,threadName);
        handler.post(displayTask);
    }
}
