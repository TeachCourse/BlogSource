package cn.teahcourse.upversion;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.io.File;

/**
 * Created by http://teachcourse.cn on 2018/1/9.
 */

public abstract class BaseServicePresenter implements IContract.Presenter {
    private IContract.View view;
    private ServiceConnection conn;

    public BaseServicePresenter(IContract.View view) {
        this.view = view;
    }

    /**
     * 请求网络
     * 获取网络版本号
     * 获取成功后与本地版本号比对
     * 符合更新条件就控制view弹窗
     */
    @Override
    public void checkUpdate(String oldVersion, String newVersion) {
        //假设获取得到最新版本
        //一般还要和忽略的版本做比对。。这里就不累赘了
        String ignore = SpUtils.getInstance(view.getAppContext()).getString("ignore");
        if (!ignore.equals(newVersion) && !ignore.equals(oldVersion)) {
            view.showUpdate(newVersion);
        }
    }

    /**
     * 设置忽略版本
     */
    @Override
    public void setIgnore(String version) {
        SpUtils.getInstance(view.getAppContext()).putString("ignore", version);
    }

    /**
     * 模拟网络下载
     */
    @Override
    public void downApk(Context context, final String url) {

        if (conn == null)
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    APKDownloadService.DownloadBinder binder = (APKDownloadService.DownloadBinder) service;
                    final APKDownloadService myService = binder.getService();

                    final IContract.INotify notifyPresent =getNotify(myService);
                    myService.load(url, new APKDownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {
                            notifyPresent.prepare();
                        }

                        @Override
                        public void onProgress(int progress) {
                            view.showProgress(progress);
                            String title = "正在下载：新版本...";
                            notifyPresent.progress(title, progress);
                        }

                        @Override
                        public void onComplete(File file) {
                            view.showComplete();
                            String title = myService.getPackageName();
                            String content = "下载完成，点击安装";
                            notifyPresent.complete(title, content, install(file));
                        }

                        @Override
                        public void onFail(String msg) {
                            view.showFail(msg);
                            String content = "新版本";
                            notifyPresent.fail(content, msg);
                        }

                        @Override
                        public Intent install(File file) {
                            return view.install(file);
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    //意味中断，较小发生，酌情处理
                }
            };
        Intent intent = new Intent(context, APKDownloadService.class);
        context.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void unbind(Context context) {
        context.unbindService(conn);
    }
    public abstract IContract.INotify getNotify(APKDownloadService service);
}
