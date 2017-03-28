package com.dev.httplib.http.request;

import com.dev.httplib.http.annotation.ReqParam;
import com.dev.httplib.http.annotation.SecurityType;

import java.io.InputStream;
import java.util.List;

/**
 * 上传文件
 */
public class UploadRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    // @ReqParam(security = SecurityType.Des)
    public String udid;
    // @ReqParam(security = SecurityType.Des)
    public String uid;
    @ReqParam(security = SecurityType.Rsa)
    public String token;

    @ReqParam(asParam = false)
    // public InputStream file;
    // public String paramName;
    // public String fileName;
    public List<SubmitStream> submit;

    public static class SubmitStream {

        public String fileName;
        public String paramName;
        public InputStream file;

    }

}
