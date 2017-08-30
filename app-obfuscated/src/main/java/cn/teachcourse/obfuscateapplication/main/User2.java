package cn.teachcourse.obfuscateapplication.main;

/**
 * Created by http://teachcourse.cn on 2017/8/29.
 */

public class User2 {
    private String name;
    private String psw;
    private String address;

    public User2(String name, String psw, String address) {
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
