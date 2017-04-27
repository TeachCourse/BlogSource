package cn.teachcourse.bean;

/**
 * Created by postmaster@teachcourse.cn on 2017/4/22.
 */

public class Button{
    public OnClickListener onClickListener;
    private String content;
    Student student;

    public void getText(){
        System.out.println("获取按钮是的文本内容。。。");
    }
}
