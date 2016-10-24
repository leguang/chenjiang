package com.shtoone.chenjiang.mvp.model;

import com.shtoone.chenjiang.BaseApplication;
import com.shtoone.chenjiang.utils.NetworkUtils;
import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class CacheInterceptor implements Interceptor {
    private static final String TAG = CacheInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetworkUtils.isConnected(BaseApplication.mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        int tryCount = 0;

        Response response = chain.proceed(request);

        KLog.e(response.toString());
        KLog.json(response.body().string());

        while (!response.isSuccessful() && tryCount < 3) {

            KLog.e("interceptRequest is not successful - :{}", tryCount);

            tryCount++;

            // retry the request
            response = chain.proceed(request);
        }

        if (NetworkUtils.isConnected(BaseApplication.mContext)) {
            int maxAge = 0;
            // 有网络时, 不缓存, 最大保存时长为0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
