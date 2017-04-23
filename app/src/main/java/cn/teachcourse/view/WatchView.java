package cn.teachcourse.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by postmaster@teachcourse.cn on 2017/4/23.
 */

public abstract class WatchView extends View {
    public WatchView(Context context) {
        super(context);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public abstract void paint(Canvas canvas);
}
