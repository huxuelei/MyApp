package com.dev.retrofitlib.download.core;

import com.dev.retrofitlib.common.HttpConfig;
import com.dev.retrofitlib.net.mode.ApiHost;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description: 下载Retrofit
 */
public class DownRetrofit {

    private DownRetrofit() {
    }

    public static Retrofit getInstance() {
        return SingletonFactory.INSTANCE;
    }

    private static class SingletonFactory {
        private static final Retrofit INSTANCE = create();

        private static Retrofit create() {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.connectTimeout(HttpConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            return new Retrofit.Builder().baseUrl(ApiHost.getHost())
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }
}
