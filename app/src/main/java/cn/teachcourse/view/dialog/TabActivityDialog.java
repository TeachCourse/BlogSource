package cn.teachcourse.view.dialog;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.teachcourse.R;
import cn.teachcourse.bean.TestDialogBean;
import cn.teahcourse.baseutil.DensityUtil;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public class TabActivityDialog extends TabActivity {
    private static final String TAG = "TabActivityDialog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createDialog(new TestDialogBean(TAG)));
    }
    /**
     * 设计弹窗
     *
     * @param bean 弹窗内容
     */
    private View createDialog(TestDialogBean bean) {
        /**LinearLayout默认水平方向布局*/
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.px2dip(this,400)));
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView mTitle_tv = new TextView(this);
        mTitle_tv.setGravity(Gravity.CENTER);
        ll.addView(mTitle_tv);

        ImageView mPic_iv = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
        mPic_iv.setLayoutParams(lp);
        ll.addView(mPic_iv);

        if (bean != null) {
            mTitle_tv.setText(bean.getContent());
            mPic_iv.setImageDrawable(getResources().getDrawable(R.drawable.network_error));
        }
        return ll;
    }
}
