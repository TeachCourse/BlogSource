package cn.teachcourse.interfaces;

import java.util.List;

/**接口定义：
 * 1、接口定义的方法必须是抽象方法
 * 2、接口无法定义常量，变量和非抽象方法
 * Created by postmaster@teachcourse.cn on 2016/5/11.
 */
public interface OnMsgChangeListener {
    public void doMsg(List<MsgBean> listBean);//处理消息
}
