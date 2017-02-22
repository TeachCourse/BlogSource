package cn.teachcourse.api;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by postmaster@teachcourse.cn on 2016/8/17.
 */
public class NewsDataAPI extends BaseAPI {
    private static final String TAG = "NewsDataAPI";
    private static final String URL_BYCODE = "/app/cms/listByCode";

    /**
     * 以code方法获取新闻列表
     *
     * @param code            栏目编号
     * @param offset          偏移量
     * @param rows            新闻条数
     * @param direction       刷新的方向
     * @param oCode           所属部门编号
     * @param areaId          地区
     * @param responseHandler
     */
    public static void getListDataByCode(String code, String offset,
                                         String rows, String direction, String oCode, String areaId,
                                         AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("code", code);
        params.put("rows", rows);
        if (!TextUtils.isEmpty(offset)) {
            params.put("offset", offset);
        }
        if (!TextUtils.isEmpty(direction)) {
            params.put("direction", direction);
        }
        if (!TextUtils.isEmpty(oCode)) {
            params.put("oCode", oCode);
        }
        if (!TextUtils.isEmpty(areaId)) {
            params.put("areaId", areaId);
        }
        Log.d(TAG, BASE_URL + URL_BYCODE + "?" + params.toString());
        client.get(BASE_URL + URL_BYCODE, params, responseHandler);
    }
}
