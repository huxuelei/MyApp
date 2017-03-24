package com.sidney.app;


import com.net.okhttp.Constants;
import com.net.okhttp.OkHttpFinal;
import com.net.okhttp.OkHttpFinalConfiguration;
import com.net.okhttp.Part;
import com.sidney.devlib.AppApplication;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;

/**
 * Created by 20164237 on 2016/4/19.
 */
public class MyApplication extends AppApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpFinal();

    }

    private void initOkHttpFinal() {

        List<Part> commomParams = new ArrayList<>();
        Headers commonHeaders = new Headers.Builder().build();

        List<Interceptor> interceptorList = new ArrayList<>();
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder()
                .setCommenParams(commomParams)
                .setCommenHeaders(commonHeaders)
                .setTimeout(Constants.REQ_TIMEOUT)
                .setInterceptors(interceptorList)
                //.setCookieJar(CookieJar.NO_COOKIES)
                //.setCertificates(...)
                //.setHostnameVerifier(new SkirtHttpsHostnameVerifier())
                .setDebug(true);
        //addHttps(builder);
        OkHttpFinal.getInstance().init(builder.build());
    }
}
