package cn.teachcourse.framework.okhttp3;

/**
 * Created by http://teachcourse.cn on 2017/9/26.
 */

public class ProgressModel {
    private long totalBytesRead;
    private long contentLength;
    private boolean isDone;

    public ProgressModel(long totalBytesRead, long contentLength, boolean isDone) {
        this.totalBytesRead=totalBytesRead;
        this.contentLength=contentLength;
        this.isDone=isDone;
    }

    public long getTotalBytesRead() {
        return totalBytesRead;
    }

    public void setTotalBytesRead(long totalBytesRead) {
        this.totalBytesRead = totalBytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
