package cn.teachcourse.common;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.teachcourse.R;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/5.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public abstract String getUrl();

    public static final String URL = "http://teachcourse.cn";

    public void initCommon(View view) {
            view.findViewById(R.id.activity_common_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (intent == null) {
                        intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                    }
                    intent.setData(Uri.parse(getUrl() == null ? URL : getUrl()));
                    startActivity(intent);
                }
            });
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(R.anim.activity_anim_enter, R.anim.activity_anim_enter);
    }
}
