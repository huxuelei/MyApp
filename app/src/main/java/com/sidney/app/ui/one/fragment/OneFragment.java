package com.sidney.app.ui.one.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.net.okhttp.BaseHttpRequestCallback;
import com.net.okhttp.FileDownloadCallback;
import com.net.okhttp.HttpRequest;
import com.net.okhttp.HttpTaskHandler;
import com.net.okhttp.ILogger;
import com.net.okhttp.RequestParams;
import com.net.okhttp.utils.JsonFormatUtils;
import com.sidney.app.R;
import com.sidney.app.http.MyBaseHttpRequestCallback;
import com.sidney.app.response.TestResponse;
import com.sidney.app.response.UploadResponse;
import com.sidney.app.ui.one.activity.VmActivity;
import com.sidney.devlib.ui.BaseFragment;

import java.io.File;

/**
 *
 */
public class OneFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private Button btn_get, btn_post, btn_vm;


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one, container, false);
        init();
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        findViewById();
        setOnClickListener();
    }

    private void findViewById() {
        btn_get = mView.findViewById(R.id.btn_get);
        btn_post = mView.findViewById(R.id.btn_post);
        btn_vm = mView.findViewById(R.id.btn_vm);
    }

    private void setOnClickListener() {
        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_vm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_post:

                break;
            case R.id.btn_vm:
                VmActivity.launch(mContext);
                break;
        }
    }

    /**
     * get
     */
    private void get() {
        String url = "http:/172.16.10.13/consultant-app-web/account/checkoutPhone.do";
        RequestParams params = new RequestParams();
        params.addFormDataPart("phone", "18675583187");
        HttpRequest.get(url, params, new MyBaseHttpRequestCallback<TestResponse>() {
            @Override
            protected void onSuccess(TestResponse response) {
                super.onSuccess(response);
                if (1 == response.getStatus()) {
                    ILogger.d("返回数据===" + JsonFormatUtils.formatJson(response.getData()));
                    Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                } else if (0 == response.getStatus()) {

                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }
        });
    }

    /**
     * 上传事例
     */
    private void uploadFile(File file) {
        String userId = "3097424";
        RequestParams params = new RequestParams();
        params.addFormDataPart("file", file);
        params.addFormDataPart("userId", userId);
        params.addFormDataPart("token", "NTCrWFKFCn1r8iaV3K0fLz2gX9LZS1SR");
        String fileuploadUri = "http://uploader.paojiao.cn/avatarAppUploader?userId=" + userId;

        HttpRequest.post(fileuploadUri, params, new BaseHttpRequestCallback<UploadResponse>() {
            @Override
            public void onSuccess(UploadResponse uploadResponse) {
                super.onSuccess(uploadResponse);
                Toast.makeText(mContext, "上传成功：" + uploadResponse.getData(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Toast.makeText(mContext, "上传失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress, long networkSpeed, boolean done) {

            }
        });
    }

    /**
     * 下载事例
     */
    public void download() {
        String url = "";
        HttpRequest.download(url, new File("/sdcard/rootexplorer_140220.apk"), new FileDownloadCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onProgress(int progress, long networkSpeed) {
                super.onProgress(progress, networkSpeed);
                //mPbDownload.setProgress(progress);
                //String speed = FileUtils.generateFileSize(networkSpeed);
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDone() {
                super.onDone();
                Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }
}
