package cn.teachcourse.common;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.teachcourse.R;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/5.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String URL = "http://teachcourse.cn";
    protected View mBrowserBtn_rl;

    public String getUrl() {
        return URL;
    }

    public void initButton(View view) {
        mBrowserBtn_rl = view.findViewById(R.id.activity_common_layout);
        mBrowserBtn_rl.setOnClickListener(new View.OnClickListener() {
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

}
