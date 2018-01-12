package cn.teachcourse.framework.imageloader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import cn.teachcourse.R;
import cn.teachcourse.api.DownloadImgAPI;
import cn.teachcourse.common.BaseActivity;
import cn.teachcourse.support.v4.viewholder.MyViewHolder;

public class CacheBitmapActivity extends BaseActivity {
    private static final String TAG = "CacheBitmapActivity";
    private GridView mGridView;//从缓存加载图片
    private CacheBitmapAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_bitmap);
        initView();
        initData();
    }

    /**
     * 初始化布局控件
     */
    private void initView() {
        initButton(getWindow().getDecorView());
        mGridView = (GridView) findViewById(R.id.cache_grideview);
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    @Override
    public String getUrl() {
        return null;
    }

    private class CacheBitmapAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> mList;

        public CacheBitmapAdapter(Context mContext, List<String> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mList != null) {
                return mList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_text_img, parent, false);
                viewHolder = new MyViewHolder();
                viewHolder.textView.setVisibility(View.GONE);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            ImageView imageView = viewHolder.imageView;
            DownloadImgAPI.setImageViewSync(imageView, mList.get(position));
            return convertView;
        }
    }
}
