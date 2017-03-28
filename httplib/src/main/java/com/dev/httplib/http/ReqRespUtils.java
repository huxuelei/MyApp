package com.dev.httplib.http;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.dev.httplib.AppApplication;
import com.dev.httplib.http.annotation.ReqParam;
import com.dev.httplib.http.annotation.RespText;
import com.dev.httplib.http.annotation.SecurityType;
import com.dev.httplib.http.request.BaseRequest;
import com.dev.httplib.http.response.FailResponse;
import com.dev.httplib.http.response.IJsonObj;
import com.dev.httplib.http.response.JsonStrResponse;
import com.dev.httplib.security.AppSecurityManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ReqRespUtils {

    /**
     * 生成Get请求的url
     *
     * @return 返回url数组，一个是加密的，一个是非加密的 new String[]{"url加密","url"}
     * @ReqParam request
     */
    public static String[] makeGetUrl(BaseRequest request) {
        List<KeyValue> params = request.getParams();
        if (params == null) {
            params = new ArrayList<KeyValue>();
        }

        Class<?> cls = request.getClass();
        Field[] fs = cls.getFields();

        for (Field f : fs) {
            int modi = f.getModifiers();
            if (Modifier.isStatic(modi)) {
                continue;
            }
            ReqParam reqParam = f.getAnnotation(ReqParam.class);
            if (reqParam != null && !reqParam.asParam()) {
                // 不作为参数传递
                continue;
            }
            KeyValue kv = new KeyValue();
            SecurityType type = SecurityType.None;
            if (reqParam == null || TextUtils.isEmpty(reqParam.value())) {
                kv.key = f.getName();
            } else {
                kv.key = reqParam.value();
            }

            if (reqParam != null) {
                type = reqParam.security();
            }
            kv.securityType = type;

            f.setAccessible(true);
            try {
                Object obj = f.get(request);
                if (obj == null) {
                    if (reqParam != null) {
                        if (reqParam.required()) {
                            Log.e("=====", f.getName()
                                    + " is null,but it is required.");
                            kv.value = "";
                            params.add(kv);
                        } else {
                            // 为空则不处理，不作为参数传递
                            // kv.value = "";
                        }
                    } else {
                        kv.value = null; // ?
                        params.add(kv);
                    }
                } else {
                    kv.value = obj.toString();
                    params.add(kv);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        StringBuffer enSb = new StringBuffer();
        StringBuffer normalSb = new StringBuffer();
        boolean isFirst = true;
        for (KeyValue kv : params) {
            if (isFirst) {
                isFirst = false;
            } else {
                enSb.append("&");
                normalSb.append("&");
            }

            String v1 = encrpty(request, kv);
            String v2 = TextUtils.isEmpty(kv.value) ? "" : kv.value;

            enSb.append(kv.key + "=" + encode(v1));
            normalSb.append(kv.key + "=" + encode(v2));
        }
        if (TextUtils.isEmpty(request.url)) {
            Log.w("======", request.getClass() + "'s url is empty, please check!!!");
        }
        String geturl1 = request.url + "?" + enSb.toString();
        String geturl2 = request.url + "?" + normalSb.toString();
        String[] res = new String[]{geturl1, geturl2};
        return res;
    }

    // 对字段进行加密
    static String encrpty(BaseRequest request, KeyValue kv) {
        SecurityType type = kv.securityType;
        String res = null;
        String value = kv.value;
        if (TextUtils.isEmpty(value)) {
            if (type != SecurityType.None) {
                Log.w("========", kv.key + " in " + request
                        + " is empty, but it's security type is " + type + ".");
            }
            res = "";
        } else {
            switch (type) {
                case Des:
                    res = AppSecurityManager.getInstance().desEncrypt(
                            request.token, value);
                    break;
                case Rsa:
                    res = AppSecurityManager.getInstance().rsaEncrypt(value);
                    break;
                default:
                    res = value;
                    break;
            }
        }
        return res;
    }

    static String encode(String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void showResp(JsonStrResponse jsonResponse) {
        if (!TextUtils.isEmpty(jsonResponse.error)) {
            Toast.makeText(AppApplication.getInstance(), jsonResponse.error, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean showRespException(Exception e) {
        if (FailResponse.NET_EX.exception.equals(e)) {
            Toast.makeText(AppApplication.getInstance(), "网络较差，请稍后重试。", Toast.LENGTH_SHORT).show();
            return true;
        } else if (FailResponse.JSON_EX.exception.equals(e)) {
            // McToastUtil.show("Json转化出错，可能是服务器问题，稍后重试。");
            Toast.makeText(AppApplication.getInstance(), "网络较差，请稍后重试。", Toast.LENGTH_SHORT).show();
            return true;
        } else if (FailResponse.TOKEN_EX.exception.equals(e)) {
            Toast.makeText(AppApplication.getInstance(), "登录状态失效，请重新登录。", Toast.LENGTH_SHORT).show();
            return true;
        } else if (FailResponse.PWD_EX.exception.equals(e)) {// 用户被锁定

        }
        Toast.makeText(AppApplication.getInstance(), "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();// e.getMessage()
        return false;
    }

    public static <T extends IJsonObj> void decrptyJsonObj(T jsonObj,
                                                           String token) {
        Field[] fs = jsonObj.getClass().getDeclaredFields();
        for (Field f : fs) {
            RespText respText = f.getAnnotation(RespText.class);
            if (respText != null) {
                SecurityType type = respText.security();
                try {
                    String value = (String) f.get(jsonObj);
                    if (!TextUtils.isEmpty(value)) {
                        String newValue = null;
                        switch (type) {
                            case Des:
                                newValue = AppSecurityManager.getInstance()
                                        .desDecrypt(token, value);
                                break;
                            case Rsa:
                                newValue = AppSecurityManager.getInstance()
                                        .rsaDecrypt(value);
                                break;
                            default:
                                break;
                        }
                        if (value != newValue) {
                            f.set(jsonObj, newValue);
                        }
                    } else {
                        if (type != SecurityType.None) {
                            Log.w("=====", f.getName() + " in " + jsonObj
                                    + " is null, but it's security is " + type
                                    + ".");
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}