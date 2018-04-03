package cn.teachcourse.framework.okhttp3;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by http://teachcourse.cn on 2017/9/26.
 */

public class OkHttpClientConfig {
    private ProgressListener progressListener;
    private OkHttpClient okHttpClient;
    private static OkHttpClientConfig okHttpClientConfig;

    public OkHttpClientConfig() {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse.newBuilder()
                                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                    .build();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                    .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                    .build();
    }

    public static OkHttpClientConfig getInstance() {
        if (okHttpClientConfig == null) {
            synchronized (OkHttpClientConfig.class) {
                if (okHttpClientConfig == null) {
                    okHttpClientConfig = new OkHttpClientConfig();
                }
            }
        }
        return okHttpClientConfig;
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public OkHttpClient getConfig() {
        if (progressListener == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }


}
