package com.dev.httplib.http.request;

import com.dev.httplib.http.KeyValue;
import com.dev.httplib.http.annotation.ReqParam;
import com.dev.httplib.http.annotation.SecurityType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseRequest implements IRequest {

    @ReqParam(asParam = false)
    private static final long serialVersionUID = 1L;

    /**
     * 请求的id，只是做标示使用，没有其他用途
     */
    @ReqParam(asParam = false)
    public String reqId = UUID.randomUUID().toString();

    @ReqParam(asParam = false)
    public String url;

    /**
     * 设备标示
     */
    //@ReqParam(security = SecurityType.Des)
    //public String udid;

    /**
     * 用户标示
     */
    //@ReqParam(security = SecurityType.Des)
    //public String uid;

    @ReqParam(security = SecurityType.Rsa)
    public String token;

    @ReqParam(asParam = false)
    private List<KeyValue> params;

    public List<KeyValue> getParams() {
        return params;
    }

    public BaseRequest addParam(KeyValue param) {
        if (params == null) {
            params = new ArrayList<KeyValue>();
        }
        params.add(param);
        return this;
    }

    public String toString() {
        return "BaseRequest [reqId=" + reqId + ", url=" + url + ", token="
                + token + ", params=" + params + "]";
    }
}