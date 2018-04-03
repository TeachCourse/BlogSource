package cn.teachcourse.framework.okhttp3;

/**
 * Created by http://teachcourse.cn on 2017/9/20.
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
