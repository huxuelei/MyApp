package com.dev.httplib.http.request;

import com.dev.httplib.http.KeyValue;
import com.dev.httplib.http.annotation.ReqParam;
import com.dev.httplib.http.annotation.SecurityType;

import java.util.ArrayList;
import java.util.List;

public class PostRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    // @ReqParam(security = SecurityType.Des)
    public String udid;
    // @ReqParam(security = SecurityType.Des)
    public String uid;
    @ReqParam(security = SecurityType.Rsa)
    public String token;
    @ReqParam(asParam = false)
    private List<KeyValue> postParams;

    public List<KeyValue> getPostParams() {
        return postParams;
    }

    public BaseRequest addPostParam(KeyValue param) {
        if (postParams == null) {
            postParams = new ArrayList<KeyValue>();
        }
        postParams.add(param);
        return this;
    }

    public String toString() {
        return "PostRequest [postParams=" + postParams + ", reqId=" + reqId
                + ", url=" + url + ", udid=" + udid + ", uid=" + uid
                + ", token=" + token + "]";
    }
}