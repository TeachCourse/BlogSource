package cn.teachcourse.bean.serialbean;

import java.io.Serializable;

/**
 * Created by http://teachcourse.cn on 2017/8/30.
 */

public class UserBean implements Serializable {
    private String name;
    private String psw;
    private String address;

    public UserBean(String name, String psw, String address) {
        this.name = name;
        this.psw = psw;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
