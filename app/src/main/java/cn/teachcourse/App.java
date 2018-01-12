package cn.teachcourse;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by postmaster@teachcourse.cn on 2016/8/17.
 */
public class App extends Application {
    private static final String CACHE = "imageLoader/Cache";

    public static App application;
    private ImageLoader imageLoader;

    public static App getApplication() {
        return application;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config(getApplicationContext()));
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
    private ImageLoaderConfiguration config(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, CACHE);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        ImageLoaderConfiguration options = new ImageLoaderConfiguration.Builder(
                context)
                .memoryCacheExtraOptions(480, 800)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .build();

        return options;
    }
}
