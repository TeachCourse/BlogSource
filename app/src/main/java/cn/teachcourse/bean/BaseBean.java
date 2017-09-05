package cn.teachcourse.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by http://teachcourse.cn on 2017/6/2.
 */

public class BaseBean implements Serializable {
    private String name;

    public BaseBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
