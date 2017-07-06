package cn.teachcourse.strategy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.teachcourse.strategy.impl.DefaultClockImpl;
import cn.teachcourse.strategy.impl.DesignClockImpl;
import cn.teachcourse.strategy.impl.NormalClockImpl;


public class WatchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        defaultStrategy();
//         normalStrategy();
        designStrategy();

        cn.teachcourse.strategy.WatchViewImpl watchView=new cn.teachcourse.strategy.WatchViewImpl(this);
        /*第一种策略：默认算法*/
        IClock defaultClock=new DefaultClockImpl(watchView);
        /*第二种策略：常用算法*/
        IClock normalClock=new NormalClockImpl(watchView);
        /*第二种策略：设计算法*/
        IClock designClock = new DesignClockImpl(watchView);

        /*自由选择使用其中的一种策略，比如：normalClock*/
        watchView.setIClock(designClock);
        setContentView(watchView);
    }

    private void defaultStrategy() {
    /*第一种策略：默认算法*/
        cn.teachcourse.strategy.WatchViewImpl watchView=new cn.teachcourse.strategy.WatchViewImpl(this);
        IClock defaultClock=new DefaultClockImpl(watchView);
        watchView.setIClock(defaultClock);

    }

    private void normalStrategy() {
    /*第二种策略：常用算法*/
        cn.teachcourse.strategy.WatchViewImpl watchView=new cn.teachcourse.strategy.WatchViewImpl(this);
        IClock normalClock=new NormalClockImpl(watchView);
        watchView.setIClock(normalClock);
        setContentView(watchView);
    }

    private void designStrategy() {
    /*第二种策略：设计算法*/
        cn.teachcourse.strategy.WatchViewImpl watchView = new cn.teachcourse.strategy.WatchViewImpl(this);
        IClock designClock = new DesignClockImpl(watchView);
        watchView.setIClock(designClock);
        setContentView(watchView);
    }

}
