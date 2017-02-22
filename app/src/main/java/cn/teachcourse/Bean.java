package cn.teachcourse;

import cn.teachcourse.common.BaseActivity;

/**
 * Created by postmaster@teachcourse.cn on 2016/6/7.
 */
public class Bean {
    private BaseActivity baseActivity;
    private String description;

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bean(String description,BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        this.description = description;
    }
}
