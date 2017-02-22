package cn.teachcourse.interfaces;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import cn.teachcourse.interfaces.util.MessageService;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/12.
 */
public class MsgData {
    private OnMsgChangeListener listener;
    private Context context;

    public MsgData(Context context) {
        this.context = context;

    }

    public void setOnMsgChangeListener(OnMsgChangeListener listener){
        this.listener=listener;
        try {
            List<MsgBean> list=MessageService.getListBean(context.getResources().getAssets().open("msgbean.xml"));
            if(list==null){
                Toast.makeText(context,"解析数据失败。。。",Toast.LENGTH_LONG).show();
                return;
            }
            listener.doMsg(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
