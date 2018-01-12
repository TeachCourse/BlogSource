package cn.teahcourse.baseutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private Toast toast;
    private static ToastUtil instance = null;

    @SuppressLint("ShowToast")
    private ToastUtil(Context context) {
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
    }

    public static ToastUtil getInstance(Context context) {
        ToastUtil toastUtil = instance;
        if (toastUtil == null) {
            synchronized (ToastUtil.class) {
                toastUtil = instance;
                if (toastUtil == null) {
                    toastUtil = new ToastUtil(context);
                    instance = toastUtil;
                }
            }
        }
        return instance;
    }

    /**
     * @param resId    Update the text in a Toast that was previously created using
     *                 one of the makeText() methods.
     * @param duration Set how long to prepare the view for
     * @author <a href="sxh_droid@163.com">Shaohui Xiao</a>
     * @version 2015年5月6日 下午3:06:44
     */
    public void show(int duration, int resId) {
        toast.setDuration(duration);
        toast.setText(resId);
        toast.show();
    }

    /**
     * @param duration Set how long to prepare the view for
     * @param s        Update the text in a Toast that was previously created using
     *                 one of the makeText() methods.
     * @author <a href="sxh_droid@163.com">Shaohui Xiao</a>
     * @version 2015年5月6日 下午3:06:44
     */
    public void show(int duration, CharSequence s) {
        toast.setDuration(duration);
        toast.setText(s);
        toast.show();
    }

    public void show(int resId) {
        show(Toast.LENGTH_SHORT, resId);
    }

    public void show(CharSequence s) {
        show(Toast.LENGTH_SHORT, s);
    }

    public void cancel() {
        toast.cancel();
    }
}
