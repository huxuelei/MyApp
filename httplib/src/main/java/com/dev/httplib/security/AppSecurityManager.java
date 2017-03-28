package com.dev.httplib.security;

import android.text.TextUtils;
import android.util.Log;

import com.dev.httplib.security.des.DESManager;
import com.dev.httplib.security.md5.Md5Util;
import com.dev.httplib.security.rsa.RSAKey;
import com.dev.httplib.security.rsa.RSATool;
import com.dev.httplib.security.rsa.RSAToolFactory;
import com.dev.httplib.security.utils.Base64;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * App加解密管理类
 */
public class AppSecurityManager {

    public static final String DEFAULT_DES_KEY = "FhzhyeXDqghfve4jRGg4PJizwJwkCSQD";

    private static AppSecurityManager instance;

    public static AppSecurityManager getInstance() {
        if (instance == null) {
            instance = new AppSecurityManager();
        }
        return instance;
    }

    // boolean isUseDefault = true;
    // String desToken;
    // DESManager desManager;
    RSAKey mRSAPublicKey;
    RSATool mRSATool;

    private AppSecurityManager() {
        checkRsa();
    }

    void checkRsa() {

        if (mRSAPublicKey == null) {
            mRSATool = RSAToolFactory.getRSATool();
//            SharedPreferences sp = AppSharePreference
//                    .getDefaultSharedPreferences();
//            String rsaKey = sp.getString(DefaultConst.RSAKEY, "");
            String rsaKey = "";
            Log.i("rsakey = ", rsaKey);
            if (!TextUtils.isEmpty(rsaKey)) {
                try {
                    mRSAPublicKey = mRSATool.loadPublicKey(rsaKey);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * rsa加密,返回的字段是没有经过urlencoder编码的
     *
     * @return
     * @ReqParam str
     */
    public String rsaEncrypt(String str) {
        checkRsa();
        byte[] encryToken = null;
        try {
            encryToken = mRSATool.encryptWithKey(str.getBytes(), mRSAPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", e.toString());
        }
        // String base64EncrypToken = Base64.encode(encryToken);
        // String urlEncodeToken = URLEncoder.encode(base64EncrypToken,
        // "utf-8");
        if (encryToken == null) {
            return "";
        } else {
            return Base64.encode(encryToken);
        }
    }

    /**
     * rsa解密
     *
     * @return
     * @ReqParam str
     */
    public String rsaDecrypt(String str) {
        checkRsa();
        try {
            return new String(mRSATool.decryptWithKey(Base64.decode(str),
                    mRSAPublicKey));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", e.toString());
        }
        return null;
    }

    /**
     * des加密,返回的字段是没有经过urlencoder编码的
     *
     * @return
     * @ReqParam token 发送请求时候传递的token，如果没有则为空
     * @ReqParam source
     */
    public String desEncrypt(String token, String source) {
        DESManager desManager = getDesManager(token);
        try {
            // return URLDecoder.decode(desManager.encrypt(source));
            return desManager.encrypt(source);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", e.toString());
        }
        return null;
    }

    /**
     * des解密
     *
     * @return
     * @ReqParam token 发送请求时候传递的token，如果没有则为空
     * @ReqParam source
     */
    public String desDecrypt(String token, String source) {
        DESManager desManager = getDesManager(token);
        try {
            return desManager.decrypt(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // public String desDecryptWithURLDecoder(String s) {
    // check();
    // try {
    // return desManager.decryptWithURLDecoder(s);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    static DESManager getDesManager(String token) {
        if (TextUtils.isEmpty(token)) {
            token = DEFAULT_DES_KEY;
        }
        token = token + token;
        Log.i("desToken = ", token);

        String md5Token = Md5Util.strMD5(token);

        String desKey = md5Token.substring(0, 24);
        String desIv = md5Token.substring(0, 8);

        DESManager desManager = DESManager.getInstance();
        desManager.setDesKey(desManager.generateKey(desKey));
        desManager.setDesIv(desManager.generateIv(desIv));
        return desManager;
    }

    public static String desEncryptRsaKey(String source) {
        DESManager desManager = getDesManager("");
        try {
            return desManager.decrypt(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}