package cn.teachcourse.interfaces;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/11.
 */
public class MsgBean {
    private String id;//消息编号
    private String type;//消息类型
    private String message;//消息内容

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MsgBean() {
    }

    public MsgBean(String id, String type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }
}
