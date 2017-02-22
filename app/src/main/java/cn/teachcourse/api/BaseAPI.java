package cn.teachcourse.api;

import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class BaseAPI {
	public static String BASE_URL = "http://www.pinggui.gov.cn";//外网域名
//	public static String BASE_URL = "http://zh.gxpg.gov.cn";//外网域名
//	public static String BASE_URL = "http://119.29.140.235";//外网IP
//	public static String BASE_URL = "http://192.168.11.84:8080/smartpg";//绑定账号测试
//	public static String BASE_URL = "http://192.168.11.103:8070/";//微信测试地址
//	public static String BASE_URL = "http://192.168.11.101:8080";//测试地址

	protected static AsyncHttpClient client;

	/**
	 * 初始化异步HttpClient
	 */
	static {
		client = new AsyncHttpClient();
	}

	/**
	 * 设置http请求超时时间，默认为60s
	 * @param timeOut
	 */
	protected static void setTimeOut(int timeOut) {
		client.setTimeout(timeOut);
	}

}
