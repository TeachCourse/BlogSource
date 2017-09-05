package cn.teachcourse.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

import cn.teachcourse.R;
import cn.teachcourse.utils.DisplayUtil;

/**
 * Created by http://teachcourse.cn on 2017/5/25.
 */

public class RadarView extends View {
    private Paint mPaint;
    private Paint mPaintC;
    private Paint mPaintH;

    private float mRotate;
    private Matrix mMatrix = new Matrix();
    private Bitmap mCenterIcon;//最中间icon
    private Shader mShader;
    private boolean mDoTiming;
    private static float[] circleProportion = {1 / 5f, 2 / 5f, 3 / 5f, 4 / 5f, 5 / 5f};//每个圆圈所占的比例
    private float mMaxRadius;//可绘制圆的最大半径
    private float mStrokeWidth;//可绘制每层圆线条宽度
    private float mX, mY;//可绘制圆的圆心
    private Bitmap mCacheBitmap;//可缓存的位图
    private Canvas mCanvas;//可缓存的位图

    public RadarView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        mStrokeWidth = DisplayUtil.dp2px(context, 0.5f);
        initP();
    }


    private void initP() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintH = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintH.setColor(Color.parseColor("#F5F5F5"));

        mPaintC = new Paint();
        mPaintC.setColor(Color.parseColor("#DCDCDC"));
        mPaintC.setStrokeWidth(mStrokeWidth);
        mPaintC.setStyle(Paint.Style.STROKE);
        mPaintC.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mShader == null) {
            float x = getMeasuredWidth()/2 ;
            float y = getMeasuredHeight()/2 ;
            float radius = Math.max(x, y);
            mMaxRadius = radius;
            mX = x;
            mY = y;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawS(canvas);
        drawC(canvas);
        drawH(canvas);
    }

    /**
     * 绘制每层圆环
     *
     * @param canvas
     */
    private void drawC(Canvas canvas) {
        Paint paint = mPaintC;
        final float x = mX;
        final float y = mY;
        final float radius = mMaxRadius;

        for (float size:circleProportion) {
            canvas.drawCircle(x, y, radius * size, paint);
        }
    }

    /**
     * 绘制最小圆头像
     *
     * @param canvas
     */
    private void drawH(Canvas canvas) {
        Paint paint = mPaintH;
        final float x = mX;
        final float y = mY;
        final float radius = mMaxRadius;

        if (mCacheBitmap == null) {
            mCenterIcon = BitmapFactory.decodeResource(getResources(), R.drawable.sex_girl);
            mCacheBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mCacheBitmap);
            float r = radius * circleProportion[0];
            mCanvas.drawCircle(x, y, r-mStrokeWidth, paint);
        }
        int layer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mCenterIcon, 0, 0, null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mCacheBitmap, 0, 0, paint);
        canvas.restoreToCount(layer);
    }

    /**
     * 绘制雷达扫描效果
     *
     * @param canvas
     */
    private void drawS(Canvas canvas) {
        Paint paint = mPaint;
        final float x = mX;
        final float y = mY;
        final float radius = mMaxRadius;
        if (mShader == null) {
            mShader = new SweepGradient(x, y, new int[]{Color.TRANSPARENT, Color.parseColor("#E1FFFF")}, null);
            mPaint.setShader(mShader);
        }
        canvas.drawColor(Color.WHITE);

        mMatrix.setRotate(mRotate, x, y);
        mShader.setLocalMatrix(mMatrix);
        mRotate += 0.5;
        if (mRotate >= 360) {
            mRotate = 0;
        }
        invalidate();

        if (mDoTiming) {
            long now = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                canvas.drawCircle(x, y, radius, paint);
            }
            now = System.currentTimeMillis() - now;
            android.util.Log.d("skia", "sweep ms = " + (now / 20.));
        } else {
            canvas.drawCircle(x, y, radius, paint);
        }
    }
}
