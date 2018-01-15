package cn.teahcourse.upversion;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.File;

/**
 * Created by http://teachcourse.cn on 2018/1/5.
 */

public interface IContract {

    interface View {
        void showUpdate(String version);

        void showProgress(int progress);

        void showFail(String msg);

        void showComplete();

        Intent install(File file);

        Context getAppContext();

        void onDestroy();

        void checkUpdate(String oldVersion, String newVersion);
    }

    interface Presenter {
        void checkUpdate(String oldVersion, String newVersion);

        void setIgnore(String version);

        void downApk(Context context, String url);

        void unbind(Context context);
    }

    interface INotify {
        void prepare();

        void progress(String title, int progress);

        void complete(String title, String msg, Intent pIntent);

        void clear();

        void cancel();

        void fail(String title, String msg);
    }
}
