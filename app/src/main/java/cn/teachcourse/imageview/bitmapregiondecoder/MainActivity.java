package cn.teachcourse.imageview.bitmapregiondecoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

import cn.teachcourse.R;
import cn.teachcourse.common.BaseActivity;


/**
 * 涉及到的类：
 * 1、BitmapFactory.Options
 * 2、Rect
 * 3、BitmapRegionDecoder
 * 4、RectF
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    private ImageView mImageView;
    private int width;//读取图片实际宽度
    private int height;//读取图片实际高度

    private LinearLayout ll_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmapregiondecoder);
        initCommon(getWindow().getDecorView());
        mImageView = (ImageView) findViewById(R.id.id_imageview);
        ll_view = (LinearLayout) findViewById(R.id.ll_view);
        getWidthAndHeight();
    }

    private void getWidthAndHeight() {
        TranslateImageView mTranImageView = (TranslateImageView) findViewById(R.id.translate_view);
//        LargeImageView mLargeView = (LargeImageView) findViewById(R.id.large_view);
//        new LoadImageTask().execute("qm.jpg");
        try {
            //获得图片的宽、高
            InputStream inputStream = getAssets().open("background_one.jpg");
            mTranImageView.setInputStream(inputStream);
//            mLargeView.setInputStream(inputStream);
//            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
//            tmpOptions.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
//            width = tmpOptions.outWidth;
//            height = tmpOptions.outHeight;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUrl() {
        return null;
    }

    private class LoadImageTask extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... params) {

            try {
                InputStream inputStream = getAssets().open(params[0]);

                return inputStream;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            if (inputStream == null) {
                return;
            }

            setImageView(inputStream);

        }
    }

    private void setImageView(InputStream inputStream) {
        //设置显示图片的中心区域
        try {
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Rect mRect = new Rect(0, 0, width / 2, height / 2);
            //设置X轴的偏移量
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(mRect, options);
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
