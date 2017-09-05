package cn.teachcourse.ipc;

import android.os.Environment;

/**
 * Created by http://teachcourse.cn on 2017/5/11.
 */

public class MyConstants {
    /**
     * 文件缓存根路径
     */
    public static final String FILE_PATH= Environment.getExternalStorageDirectory()+"/cache/";

    public static final String CACHE_CONTENT_PATH=FILE_PATH+"cacheFile";

    public static final String INTENT_ACTION="cn.teachcourse.intent.action.";
    public static final String INTENT_CATEGORY="cn.teachcourse.intent.category.";

}
