package cn.teachcourse.uploadpic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.teachcourse.uploadpic.util.PhotoUtil;
import cn.teahcourse.baseutil.CompressImg;

/**
 * Created by postmaster@teachcourse.cn on 2017/3/4.
 */
public class PreviewImgActivity extends AppCompatActivity {

    public static void start(AppCompatActivity context, String url) {
        Intent intent = new Intent(context, PreviewImgActivity.class);
        intent.putExtra("imgUrl", url);
        context.startActivityForResult(intent, PhotoUtil.REQUEST_CODE_PREVIEW_PHOTO);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        setContentView(imageView);

        String url = getIntent().getStringExtra("imgUrl");
        if (url != null) {
            Bitmap bitmap = CompressImg.compress(url);
            imageView.setImageBitmap(bitmap);
        }
    }
}
