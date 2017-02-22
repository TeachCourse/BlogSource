package cn.teachcourse.imageview.bitmapregiondecoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Administrator on 2016/4/26.
 */
public class TranslateImageView extends View {
    private int time=1000;
    private BitmapRegionDecoder mDecoder;
    /**
     * 图片的宽度和高度
     */
    private int mImageWidth, mImageHeight;
    /**
     * 绘制的区域
     */
    private volatile Rect mRect = new Rect();
    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    static {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    /**
     * 改变图片显示的位置
     */
    private void init(){
        time=time-10;
        Message message = mHandler.obtainMessage(0);
        mHandler.sendMessageDelayed(message, time);
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateImageView();//刷新界面
            Message message = mHandler.obtainMessage(0);
            sendMessageDelayed(message, time);
            if (time==0){
                time=1000;
            }
        }
    };

    private void updateImageView() {
        if (mImageWidth > getWidth()) {
            mRect.offset(100, 0);
            checkWidth();
            invalidate();
        }else{
            mRect.offset(-100, 0);
            checkWidth();
            invalidate();
        }
        if (mImageHeight > getHeight()) {
            mRect.offset(0, 100);
            checkHeight();
            invalidate();
        }

    }

    private void checkWidth() {

        Rect rect = mRect;
        int imageWidth = mImageWidth;

        if (rect.right > imageWidth) {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }


    private void checkHeight() {

        Rect rect = mRect;
        int imageHeight = mImageHeight;

        if (rect.bottom > imageHeight) {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    /**
     * 配置原图片的宽度和高度
     *
     * @param is
     */
    public void setInputStream(InputStream is) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            // Grab the bounds for the scene dimensions
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;
            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bm = mDecoder.decodeRegion(mRect, options);
        canvas.drawBitmap(bm, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        //默认直接显示图片的中心区域，可以自己去调节
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = imageHeight;
    }

    public TranslateImageView(Context context) {
        super(context);
    }

    public TranslateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TranslateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
