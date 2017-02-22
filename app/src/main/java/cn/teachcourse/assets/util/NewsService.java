package cn.teachcourse.assets.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import cn.teachcourse.assets.bean.NewsInfoBean;

public class NewsService {
	public static List<NewsInfoBean> getNewsBean(InputStream is)
			throws Exception {
		List<NewsInfoBean> newsList = null;
		NewsInfoBean newsBean = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(is, "UTF-8");
		int event = pullParser.getEventType();

		while (event != XmlPullParser.END_DOCUMENT) {

			switch (event) {

			case XmlPullParser.START_DOCUMENT:
				newsList = new ArrayList<NewsInfoBean>();
				break;
			case XmlPullParser.START_TAG:
				if ("news".equals(pullParser.getName())) {
					String id = pullParser.getAttributeValue(0);
					newsBean = new NewsInfoBean();
					newsBean.setId(id);
				}
				if ("title".equals(pullParser.getName())) {
					String title = pullParser.nextText();
					newsBean.setTitle(title);
				}
				if ("content".equals(pullParser.getName())) {
					String content = pullParser.nextText();
					newsBean.setContent(content);
				}
				if ("full_title".equals(pullParser.getName())) {
					String full_title = pullParser.nextText();
					newsBean.setFull_title(full_title);
				}
				if ("pdate".equals(pullParser.getName())) {
					String pdate = pullParser.nextText();
					newsBean.setPdate(pdate);
				}
				if ("src".equals(pullParser.getName())) {
					String src = pullParser.nextText();
					newsBean.setSrc(src);
				}
				if ("img_width".equals(pullParser.getName())) {
					String img_width = pullParser.nextText();
					newsBean.setImg_width(img_width);
				}
				if ("img_length".equals(pullParser.getName())) {
					String img_length = pullParser.nextText();
					newsBean.setImg_length(img_length);
				}
				if ("img".equals(pullParser.getName())) {
					String img = pullParser.nextText();
					newsBean.setImg(img);
				}
				if ("url".equals(pullParser.getName())) {
					String url = pullParser.nextText();
					newsBean.setUrl(url);
				}
				if ("pdate_src".equals(pullParser.getName())) {
					String pdate_src = pullParser.nextText();
					newsBean.setPdate_src(pdate_src);
				}

				break;

			case XmlPullParser.END_TAG:
				if ("news".equals(pullParser.getName())) {
					newsList.add(newsBean);
					newsBean = null;
				}
				break;

			}

			event = pullParser.next();
		}

		return newsList;
	}
}
