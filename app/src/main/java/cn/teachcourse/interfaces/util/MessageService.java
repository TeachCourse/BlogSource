package cn.teachcourse.interfaces.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.teachcourse.interfaces.MsgBean;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/12.
 */
public class MessageService {
    public static List<MsgBean> getListBean(String filePath){
        InputStream is= null;
        try {
            is = new FileInputStream(filePath);
            return getListBean(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<MsgBean> getListBean(InputStream is)
            throws Exception {
        List<MsgBean> newsList = null;
        MsgBean msgBean = null;
        XmlPullParser pullParser = Xml.newPullParser();//创建一个xml解析器
        pullParser.setInput(is, "UTF-8");//设置流编码
        int event = pullParser.getEventType();//获取解析器事件：1、END_DOCUMENT,2、START_DOCUMENT，3、START_TAG，4、END_TAG

        while (event != XmlPullParser.END_DOCUMENT) {

            switch (event) {

                case XmlPullParser.START_DOCUMENT:
                    newsList = new ArrayList<MsgBean>();
                    break;
                case XmlPullParser.START_TAG:
                    if ("msg".equals(pullParser.getName())) {
                        String id = pullParser.getAttributeValue(0);
                        msgBean = new MsgBean();
                        msgBean.setId(id);
                    }
                    if ("type".equals(pullParser.getName())) {
                        String type = pullParser.nextText();
                        msgBean.setType(type);
                    }
                    if ("message".equals(pullParser.getName())) {
                        String message = pullParser.nextText();
                        msgBean.setMessage(message);
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if ("msg".equals(pullParser.getName())) {
                        newsList.add(msgBean);
                        msgBean = null;
                    }
                    break;

            }
            event = pullParser.next();
        }
        return newsList;
    }
}
