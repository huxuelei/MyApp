package com.sidney.app.http;


import com.net.okhttp.BaseHttpRequestCallback;
import com.sidney.app.response.BaseResponse;

/**
 * Desction:
 */
public class MyBaseHttpRequestCallback<T extends BaseResponse> extends BaseHttpRequestCallback<T> {

    @Override
    protected void onSuccess(T t) {
        int code = t.getStatus();
        if (code == 1) {//成功

        } else {//失败

        }
    }

    @Override
    public void onFailure(int errorCode, String msg) {
        super.onFailure(errorCode, msg);
    }
}
