package com.dev.retrofitlib.net.subscriber;

import android.content.Context;

import com.dev.retrofitlib.net.callback.ApiCallback;
import com.dev.retrofitlib.net.exception.ApiException;

/**
 * @Description: 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 */
public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {

    protected ApiCallback<T> callBack;

    public ApiCallbackSubscriber(Context context, ApiCallback<T> callBack) {
        super(context);
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        callBack.onStart();
    }

    @Override
    public void onError(ApiException e) {
        callBack.onError(e);
    }

    @Override
    public void onCompleted() {
        callBack.onCompleted();
    }

    @Override
    public void onNext(T t) {
        callBack.onNext(t);
    }
}
