package cn.teachcourse.designpattern.factory;

import android.widget.TextView;

/**
 * Created by http://teachcourse.cn on 2017/8/3.
 */

class DisplayTask implements Runnable {
    private TextView textView;
    private String threadName;

    public DisplayTask(TextView textView,String threadName) {
        this.textView=textView;
        this.threadName=threadName;
    }

    @Override
    public void run() {
        textView.setText(threadName);
    }
}
