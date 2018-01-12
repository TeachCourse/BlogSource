package cn.teachcourse.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import cn.teachcourse.R;
import cn.teahcourse.baseutil.DisplayUtil;

/**
 * Created by http://teachcourse.cn on 2017/5/25.
 */

public class GuaGuaLeView extends View {
    private Random rnd;
    private Paint paint;
    private Paint clearPaint;
    private static final String[] PRIZE = {
            "恭喜您，一等奖，奖金 1 亿元",
            "恭喜您，二等奖，奖金 5000 万元",
            "恭喜您，三等奖，奖金 100 元",
            "很遗憾，您没有中奖，继续加油哦"
    };
    /**
     * 涂抹的粗细
     */
    private static final int FINGER = 50;
    /**
     * 缓冲区
     */
    private Bitmap bmpBuffer;
    /**
     * 缓冲区画布
     */
    private Canvas cvsBuffer;
    private int curX, curY;

    public GuaGuaLeView(Context context) {
        this(context, null);
    }

    public GuaGuaLeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        rnd = new Random();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(DisplayUtil.dp2px(context,18));
        paint.setColor(Color.RED);

        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        clearPaint.setStrokeJoin(Paint.Join.ROUND);
        clearPaint.setStrokeCap(Paint.Cap.ROUND);
        clearPaint.setStrokeWidth(FINGER);
        //画背景
        drawBackground();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bmpBuffer == null)
            //初始化缓冲区
            bmpBuffer = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cvsBuffer = new Canvas(bmpBuffer);
        //为缓冲区蒙上一灰色
        cvsBuffer.drawColor(Color.parseColor("#F5F5F5"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(900,widthMeasureSpec),measureSize(1200,heightMeasureSpec));
    }
    private int measureSize(int defaultSize,int measureSpec){
        int result;
        int specMode=MeasureSpec.getMode(measureSpec);
        int specSize=MeasureSpec.getSize(measureSpec);
        if(specMode==MeasureSpec.EXACTLY){
            result=specSize;
        }else{
            result=defaultSize;
            if (specMode==MeasureSpec.AT_MOST){
                result=Math.min(result,specSize);
            }
        }
        return result;
    }
    /**
     * 随机生成中奖信息
     *
     * @return 数组 PRIZE 的索引
     */
    private int getPrizeIndex() {
        return rnd.nextInt(PRIZE.length);
    }

    /**
     * 绘制背景，背景包括背景图片和中奖信息
     */
    private void drawBackground() {
        Bitmap bmpBackground = BitmapFactory.decodeResource(
                getResources(), R.drawable.sex_girl);
        //从资源中读取的 bmpBackground 不可以修改，复制出一张可以修改的图片
        Bitmap bmpBackgroundMutable =
                bmpBackground.copy(Bitmap.Config.ARGB_8888, true);
        //在图片上画上中奖信息
        Canvas cvsBackground = new Canvas(bmpBackgroundMutable);
        //计算出文字所占的区域，将文字放在正中间
        String text = PRIZE[getPrizeIndex()];
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);

        int x = (bmpBackgroundMutable.getWidth() - rect.width()) / 2;
        int y = (bmpBackgroundMutable.getHeight() - rect.height()) / 2;
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, paint);
        paint.setShadowLayer(10, 0, 0, Color.GREEN);
        cvsBackground.drawText(text, x, y, paint);
        paint.setShadowLayer(0, 0, 0, Color.YELLOW);

        //画背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(new BitmapDrawable(
                    getResources(), bmpBackgroundMutable));
        } else {
            this.setBackgroundDrawable(
                    new BitmapDrawable(bmpBackgroundMutable));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bmpBuffer, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curX = x;
                curY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                cvsBuffer.drawLine(curX, curY, x, y, clearPaint);
                invalidate();
                curX = x;
                curY = y;
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }
}
