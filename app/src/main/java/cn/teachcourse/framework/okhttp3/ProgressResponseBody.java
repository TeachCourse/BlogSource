package cn.teachcourse.framework.okhttp3;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by http://teachcourse.cn on 2017/9/20.
 */

public class ProgressResponseBody extends ResponseBody {
    public static final int UPDATE = 0x01;
    private final ResponseBody responseBody;
    private final ProgressListener progressListener;
    private BufferedSource bufferedSource;
    private Handler mHandler;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
        if (mHandler == null) {
            mHandler = new MyHandler();
        }
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //发送消息到主线程，ProgressModel为自定义实体类
                Message msg = Message.obtain();
                msg.what = UPDATE;
                msg.obj = new ProgressModel(totalBytesRead, responseBody.contentLength(), totalBytesRead == -1);
                mHandler.sendMessage(msg);
//                progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }

    /**
     * 将进度放到主线程中显示
     */
    class MyHandler extends Handler {
        public MyHandler() {
            super(Looper.getMainLooper());
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    if (progressListener != null)
                        progressListener.update(progressModel.getTotalBytesRead(), progressModel.getContentLength(), progressModel.isDone());
                    break;

            }
        }
    }
}


