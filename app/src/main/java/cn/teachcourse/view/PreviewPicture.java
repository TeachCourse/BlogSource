package cn.teachcourse.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.adapter.PPagerAdapter;
import cn.teachcourse.api.DownloadImgAPI;

public class PreviewPicture extends AppCompatActivity {
    private static final String TAG = "PreviewPicture";
    private static String paths;
    private LinkedList<CharSequence> mDataSet = new LinkedList<>();
    /*记录当前ViewPager的索引*/
    private int mCurrentIndex = 0;

    public static void start(Context context, ArrayList<CharSequence> paths) {
        Intent intent = new Intent(context, PreviewPicture.class);
        PreviewPicture.paths = "paths";
        intent.putCharSequenceArrayListExtra(PreviewPicture.paths, paths);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_picture);
        ArrayList paths = getIntent().getCharSequenceArrayListExtra("paths");
        if (paths == null || paths.size() == 0) {
            mDataSet.add("drawable://" + R.drawable.background_one);
            mDataSet.add("drawable://" + R.drawable.background_two);
            mDataSet.add("drawable://" + R.drawable.background_three);
        } else {
            mDataSet.addAll(paths);
        }
        final ViewPager viewPager = (ViewPager) findViewById(R.id.preview_pic_vp);
        final PreviewPicAdapter adapter = new PreviewPicAdapter(this, mDataSet);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                Log.d(TAG, "currentIndex=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final ImageView imageView = (ImageView) findViewById(R.id.preview_pic_delete_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.remove(mCurrentIndex);
                adapter.notifyDataSetChanged(PPagerAdapter.RefreshType.REMOVE);
                if (mDataSet.size() == 0) {
                    finish();
                }
            }
        });
    }

    public static class PreviewPicAdapter extends PPagerAdapter<CharSequence> {
        private POnClickListener pOnClickListener;

        public void setPOnClickListener(POnClickListener pOnClickListener) {
            this.pOnClickListener = pOnClickListener;
        }

        public interface POnClickListener {
            void onClick(ImageView imageView, int position);
        }

        public PreviewPicAdapter(Context mContext, List<CharSequence> mDataSet) {
            super(mContext, mDataSet);
        }

        @Override
        public Object initItem(ViewGroup container, final int position) {
            final ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            int length = mDataSet.size();
//            String item = mDataSet.get(length - position-1).toString();
            String item = mDataSet.get(position).toString();

            DownloadImgAPI.setImageViewAware(imageView, item);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pOnClickListener != null) {
                        pOnClickListener.onClick(imageView, position);
                    }
                }
            });

            return imageView;
        }

    }
}
