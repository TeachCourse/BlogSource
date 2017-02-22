package cn.teachcourse;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by postmaster@teachcourse.cn on 2016/8/17.
 */
public class MyApplication extends Application {
    private static ImageLoader mImageLoader = null;

    public static ImageLoader getmImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
        }
        return mImageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 初始化ImageLoader配置
     *
     * @param context
     */
    private static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "imageloader/Cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        ImageLoaderConfiguration options = new ImageLoaderConfiguration.Builder(
                context)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3) // 线程池内加载的数量
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO) // 将保存的时候的URI名称用MD5 加密
                .discCacheFileCount(100)
                .discCache(new UnlimitedDiskCache(cacheDir))// 缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// 自定义缓存路径
                .imageDownloader(
                        new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .build();
        getmImageLoader().init(options);
    }
}
