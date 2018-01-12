## 一、简单的用法 ##
三步快速接入自动更新功能：1、初始化Dialog，2、检查是否执行版本更新，3、解除已经绑定的后台服务
```
public class UpVersionActivity extends AppCompatActivity {
    private IContract.View mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upversion);
        String apkUrl = "http://imtt.dd.qq.com/16891/ED067C2CC1238607B951BAD4B57476C9.apk";

        //一、初始化Dialog
        mPresenter = new ViewPresenterImpl(this, apkUrl);
    }

    public void onClick(View view) {
        PermissionsUtils.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "正在下载新版本，请稍后", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsUtils.permissionCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {

                PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
                String local = pi.versionName;
                String newVersion = "2.0";
                //二、检查版本更新状态
                mPresenter.checkUpdate(local, newVersion);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            PermissionsUtils.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }


    @Override
    protected void onDestroy() {
        //三、如果开启后台下载服务，调用该方法解除绑定
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
```

## 二、定制自己喜欢的Dialog ##
实现内部接口`IContract.View`，重写对应的方法，定制自己喜欢的Dialog样式，实现逻辑可以参考`ViewPresenterImpl`类，例如：

```
IContract.View mPresenter=new ViewPresenter(this,apkUrl);
```

## 三、定制自己喜欢的Notification ##
实现内部接口`IContract.INotify`，重写对应的方法，实现逻辑可以参考`NotifyPresenterImpl`类，同时继承`BaseServicePresenter`的抽象方法`IContract.INotify getNotify()`，将通知栏实现类的对象赋值给`IContract.INOtify`对象，例如：

```
/**
 *实现getNotify()抽象方法
 */
public class ServicePresenterImpl extends BaseServicePresenter {

    public ServicePresenterImpl(IContract.View view) {
        super(view);
    }

    @Override
    public IContract.INotify getNotify(APKDownloadService service) {
        return new NotifyPresenterImpl(service);
    }
}

/**
 *定制喜欢的Notification
 */
public class NotifyPresenterImpl implements IContract.INotify {
    private Context context;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private static final int NOTIFY_ID = 0;

    public NotifyPresenterImpl(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("开始下载")
                .setContentText("正在连接服务器")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
    }

    @Override
    public void prepare() {
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
    }

    @Override
    public void complete(String title, String msg, Intent intent) {
        if (intent == null) return;
        if (onFront()) {
            cancel();
            context.startActivity(intent);
            //弹出安装窗口把原程序关闭。
            //避免安装完毕点击打开时没反应
            killProcess(android.os.Process.myPid());
        } else {
            PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext()
                    , 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pIntent)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setProgress(0, 0, false)
                    .setDefaults(Notification.DEFAULT_ALL);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(NOTIFY_ID, notification);
        }
    }

    @Override
    public void progress(String title,int progress) {
        mBuilder.setContentTitle(title)
                .setContentText(String.format(Locale.CHINESE, "%d%%", progress))
                .setProgress(100, progress, false)
                .setWhen(System.currentTimeMillis());
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFY_ID, notification);
    }

    @Override
    public void fail(String title, String msg) {
        mBuilder.setContentTitle(title)
                .setContentText(msg);
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFY_ID, notification);
    }

    @Override

    public void cancel() {
        mNotificationManager.cancel(NOTIFY_ID);
    }

    @Override
    public void clear() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
            mNotificationManager = null;
        }
        if (mBuilder != null)
            mBuilder = null;
    }

    /**
     * 是否运行在用户前面
     */
    private boolean onFront() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null || appProcesses.isEmpty())
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName()) &&
                    appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
```