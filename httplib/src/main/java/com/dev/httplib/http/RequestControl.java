package com.dev.httplib.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.dev.httplib.AppApplication;
import com.dev.httplib.http.base.ILife;
import com.dev.httplib.http.request.BaseRequest;
import com.dev.httplib.http.request.PostRequest;
import com.dev.httplib.http.request.UploadRequest;
import com.dev.httplib.http.response.BaseResponse;
import com.dev.httplib.http.response.DownLoadResponse;
import com.dev.httplib.http.response.FailResponse;
import com.dev.httplib.http.response.IResponseError;
import com.dev.httplib.http.response.JsonStrResponse;
import com.dev.httplib.http.utils.NetUtil;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RequestControl implements ILife {

    private static RequestControl instance = new RequestControl();

    public static RequestControl getInstance() {
        return instance;
    }

    ExecutorService executorService; // 线程池
    static AppApplication appContext; // 全局上下文
    Handler handler; // handler对象，负责线程通信

    // private IProcess processProxy;// 网络处理代理

    private RequestControl() {
        appContext = AppApplication.getInstance();
        handler = new ResponseHandler();
    }

    @Override
    public void init() {
        Log.i("=======", "init");
        executorService = Executors.newFixedThreadPool(4);
        // processProxy = new ProcessProxy(appContext);
    }

    public void doGet(BaseRequest request, OnRequestCallback callback) {
        this.execute(new SimpleRequestRunnable(request, callback));
    }

    public void doPost(PostRequest request, OnRequestCallback callback) {
        this.execute(new PostRequestRunnable(request, callback));
    }

    public void doUpload(UploadRequest request, OnRequestCallback callback) {
        this.execute(new UploadRequestRunnable(request, callback));
    }

    public void doDownload(BaseRequest request, OnRequestCallback callback) {
        this.execute(new DownloadRequestRunnable(request, callback));
    }

    void execute(SimpleRequestRunnable runnable) {
        Log.i("======", "execute");
        executorService.execute(runnable);
    }

    @Override
    public void unInit() {
        Log.i("======", "unInit");
        executorService.shutdown();
    }

    class SimpleRequestRunnable implements Runnable {
        BaseRequest request;
        OnRequestCallback callback;

        public SimpleRequestRunnable(BaseRequest r, OnRequestCallback c) {
            request = r;
            callback = c;
        }

        @Override
        public void run() {
            Log.i("===", "run");
            Message msg = handler.obtainMessage();
            TempResponse temp = new TempResponse();
            temp.callback = callback;
            temp.req = request;
            String reqId = request.reqId;
            boolean isConnnect = NetUtil.isConnectInternet();// 判读网络是否连接
            Log.i("===", "Is Connect Internet:" + isConnnect);
            if (isConnnect) {
                Log.i("===", "ThreadName:" + Thread.currentThread().getName());
                Log.i("===", "---------------------------------request----------------------------------");
                Log.i("===", request + "");
                Log.i("===", "---------------------------------------------------------------------------");
                BaseResponse resp = exec();// processProxy.process(request);//
                // 代理处理
                Log.i("===", "----------------------------------response---------------------------------");
                resp.respId = reqId;
                Log.i("===", resp + "");
                Log.i("===", "---------------------------------------------------------------------------");
                temp.resp = resp;
            } else {
                temp.resp = FailResponse.NET_EX;
            }
            Log.i("===", temp.resp + "");
            // 交给ui线程请求的结果
            msg.obj = temp;
            msg.sendToTarget();
        }

        protected BaseResponse exec() {
            BaseResponse resp = null;
            try {
                resp = new BaseResponse();
                HttpClient httpclient = AppHttpClient.getInstance()
                        .createClient();

                String urls[] = ReqRespUtils.makeGetUrl(request);
                Log.i("===", "encrpty request url : " + urls[0]);
                Log.i("===", "normal request url : " + urls[1]);
                String url = urls[0];

                HttpGet httpGet = new HttpGet(url);
                addClientHead(httpGet);
                HttpResponse httpResponse = httpclient.execute(httpGet);

                int code = httpResponse.getStatusLine().getStatusCode();
                Log.i("===", "http status code:" + code);
                if (code == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(
                            httpResponse.getEntity(), HTTP.UTF_8);
                    Log.i("===", "result = " + result);
                    resp.json = result;
                } else {
                    Toast.makeText(AppApplication.getInstance(), "网络异常!", Toast.LENGTH_SHORT).show();
                    // throw new Exception();
                }
            } catch (Exception e) {
                Log.e("===", "Exception", e);
                resp = new FailResponse(e);
            }
            return resp;
        }

        protected void addClientHead(AbstractHttpMessage httpMethod) {
            httpMethod.addHeader("User-Agent", "android");
        }
    }

    class UploadRequestRunnable extends SimpleRequestRunnable {

        public UploadRequestRunnable(BaseRequest r, OnRequestCallback c) {
            super(r, c);
        }

        @Override
        protected BaseResponse exec() {
            BaseResponse resp = null;
            try {
                resp = new BaseResponse();
                UploadRequest uploadRequest = (UploadRequest) request;
                HttpClient httpclient = AppHttpClient.getInstance()
                        .createClient();

                String urls[] = ReqRespUtils.makeGetUrl(uploadRequest);
                Log.i("===", "encrpty request url : " + urls[0]);
                Log.i("===", "normal request url : " + urls[1]);
                String url = urls[0];

                MultipartEntity me = new MultipartEntity();
                List<UploadRequest.SubmitStream> submit = uploadRequest.submit;
                if (submit != null && !submit.isEmpty()) {
                    for (UploadRequest.SubmitStream tempSubmit : submit) {
                        me.addPart(tempSubmit.paramName, new InputStreamBody(
                                tempSubmit.file, tempSubmit.fileName));

                    }
                }

                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(me);
                addClientHead(httpPost);

                HttpResponse httpResponse = httpclient.execute(httpPost);

                int code = httpResponse.getStatusLine().getStatusCode();
                Log.i("===", "http status code:" + code);
                if (code == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(
                            httpResponse.getEntity(), HTTP.UTF_8);
                    Log.i("===", "result = " + result);
                    resp.json = result;
                } else {
                    Toast.makeText(AppApplication.getInstance(), "网络异常!", Toast.LENGTH_SHORT).show();
                    // throw new Exception();
                }
            } catch (Exception e) {
                Log.e("===", "Exception", e);
                resp = new FailResponse(e);
            }
            return resp;
        }
    }

    class PostRequestRunnable extends SimpleRequestRunnable {

        public PostRequestRunnable(BaseRequest r, OnRequestCallback c) {
            super(r, c);
        }

        @Override
        protected BaseResponse exec() {
            BaseResponse resp = null;
            try {
                resp = new BaseResponse();
                PostRequest postRequest = (PostRequest) request;
                HttpClient httpclient = AppHttpClient.getInstance()
                        .createClient();

                String urls[] = ReqRespUtils.makeGetUrl(postRequest);
                Log.i("===", "encrpty request url : " + urls[0]);
                Log.i("===", "normal request url : " + urls[1]);
                String url = urls[0];

                MultipartEntity me = new MultipartEntity();
                List<KeyValue> list = postRequest.getPostParams();
                if (list != null) {
                    for (KeyValue kv : list) {
                        me.addPart(
                                kv.key,
                                new StringBody(ReqRespUtils.encrpty(
                                        postRequest, kv)));
                    }
                }

                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(me);
                addClientHead(httpPost);

                HttpResponse httpResponse = httpclient.execute(httpPost);

                int code = httpResponse.getStatusLine().getStatusCode();
                Log.i("===", "http status code:" + code);
                if (code == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(
                            httpResponse.getEntity(), HTTP.UTF_8);
                    Log.i("===", "result = " + result);
                    resp.json = result;
                } else {
                    Toast.makeText(AppApplication.getInstance(), "网络异常!", Toast.LENGTH_SHORT).show();
                    // throw new Exception();
                }
            } catch (Exception e) {
                Log.e("===", "Exception", e);
                resp = new FailResponse(e);
            }
            return resp;
        }
    }

    class DownloadRequestRunnable extends SimpleRequestRunnable {

        public DownloadRequestRunnable(BaseRequest r, OnRequestCallback c) {
            super(r, c);
        }

        @Override
        protected BaseResponse exec() {
            BaseResponse resp = null;
            try {
                HttpClient httpclient = AppHttpClient.getInstance()
                        .createClient();

                String urls[] = ReqRespUtils.makeGetUrl(request);
                Log.i("===", "encrpty request url : " + urls[0]);
                Log.i("===", "normal request url : " + urls[1]);
                String url = urls[0];

                HttpGet httpGet = new HttpGet(url);
                addClientHead(httpGet);

                HttpResponse httpResponse = httpclient.execute(httpGet);
                int code = httpResponse.getStatusLine().getStatusCode();
                DownLoadResponse response = new DownLoadResponse();
                Log.i("===", "http status code:" + code);
                if (code == HttpStatus.SC_OK) {
                    response.inputStream = httpResponse.getEntity()
                            .getContent();
                } else {
                    Toast.makeText(AppApplication.getInstance(), "网络异常!", Toast.LENGTH_SHORT).show();
                    // throw new Exception();
                }
                resp = response;
            } catch (Exception e) {
                Log.e("===", "Exception", e);
                resp = new FailResponse(e);
            }
            return resp;
        }
    }

    static class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i("===", getClass() + "handleMessage");
            TempResponse temp = (TempResponse) msg.obj;
            temp.doCallback();
        }
    }

    static class TempResponse {
        BaseRequest req;
        BaseResponse resp;
        OnRequestCallback callback;

        void doCallback() {
            if (callback != null) {
                if (resp instanceof IResponseError) {
                    FailResponse fr = (FailResponse) resp;
                    callback.onException(fr.exception);
                } else {
                    callback.onSuccess(req, resp);
                }
            }
        }
    }

    public interface OnRequestCallback {
        /**
         * 用于获取服务器返回的响应
         *
         * @ReqParam request 请求
         * @ReqParam response 响应
         */
        public void onSuccess(BaseRequest request, BaseResponse response);

        /**
         * 异常错误返回类
         *
         * @ReqParam e Exception
         */
        public void onException(Exception e);
    }

    public static abstract class OnRequestCallback2 implements
            OnRequestCallback {
        @Override
        public void onSuccess(BaseRequest request, BaseResponse response) {
            JsonStrResponse jsonStrResponse = null;
            try {
                jsonStrResponse = new Gson().fromJson(response.json,
                        JsonStrResponse.class);
                jsonStrResponse.json = response.json;
                jsonStrResponse.respId = response.respId;

                if (jsonStrResponse.isTokenInvalide()) {
                    onException(FailResponse.TOKEN_EX.exception);
                } else if (jsonStrResponse.isPwdInvalide()) {// 用户被锁定
                    onException(FailResponse.PWD_EX.exception);
                } else {
                    onJsonResponse(request, jsonStrResponse);
                }
            } catch (Exception e) {
                Log.e("===", "", e);
                onException(FailResponse.JSON_EX.exception);
            }
        }

        /**
         * 用于获取服务器返回的响应
         *
         * @ReqParam request 请求
         * @ReqParam jsonResponse json响应
         */
        public abstract void onJsonResponse(BaseRequest request,
                                            JsonStrResponse jsonResponse);

        @Override
        public void onException(Exception e) {
            Log.i("===", "onException");
            Log.i("===", "e-----------------------------" + e);
            if (FailResponse.TOKEN_EX.exception.equals(e)) {
//				if (MainActivity.InAppLayout) {
//					if (RequestControl.appContext != null) {
//						UserInfoPreference.getInstance().removeUserInfo(
//								appContext, "userinfo");
//						Intent intent = new Intent(appContext,
//								DialogActivity.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						appContext.startActivity(intent);
//					}
//				}
            }
            ReqRespUtils.showRespException(e);
        }
    }

}