package cn.teachcourse.framework.okhttp3;

/**
 * Created by http://teachcourse.cn on 2017/9/14.
 */

public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}
