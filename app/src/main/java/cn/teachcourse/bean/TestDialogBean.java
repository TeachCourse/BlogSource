package cn.teachcourse.bean;

import java.io.Serializable;

/**
 * Created by postmaster@teachcourse.cn on 2017/1/3.
 */

public class TestDialogBean implements Serializable {
    /**
     * 分类：0-默认分类；其他分类1、2、3...
     */
    private int type;
    private String content;
    private Class zz;

    public Class getZz() {
        return zz;
    }

    public void setZz(Class zz) {
        this.zz = zz;
    }

    public TestDialogBean(String content) {
        this.setType(0);
        this.content = content;
    }

    public TestDialogBean(String content, Class zz) {
        this.setType(0);
        this.content = content;
        this.zz = zz;
    }

    public TestDialogBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
