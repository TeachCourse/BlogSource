package cn.teachcourse.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by http://teachcourse.cn on 2017/5/24.
 */

public class PorterDuffXferView extends View {
    public PorterDuffXferView(Context context) {
        this(context,null);
    }

    public PorterDuffXferView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap dst = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Bitmap src = dst.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap b3 = Bitmap.createBitmap(450, 450, Bitmap.Config.ARGB_8888);

        Canvas c1 = new Canvas(dst);
        Canvas c2 = new Canvas(src);
        Canvas c3 = new Canvas(b3);

        Paint p1 = new Paint();
        p1.setColor(Color.GRAY);
        c1.drawCircle(150, 150, 150, p1);

        Paint p2 = new Paint();
        p2.setColor(Color.GREEN);
        c2.drawRect(0, 0, 300, 300, p2);

        //定义画笔
        Paint paint = new Paint();
        //创建图层
        int layer = c3.saveLayer(150, 150, 450, 450, null, Canvas.ALL_SAVE_FLAG);
        //画圆
        c3.drawBitmap(dst, 0, 0, null);
        //定义位图的运算模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        //画正方形
        c3.drawBitmap(src, 150, 150, paint);
        //清除运算效果
        paint.setXfermode(null);
        //恢复
        c3.restoreToCount(layer);

        //绘制到 Canvas 上
        canvas.drawBitmap(b3, 0, 0, null);
    }
}
