package com.dev.httplib.security.des;

import android.util.Log;

import java.net.URLDecoder;

/**
 * @类功能说明：3DES 加解密管理类
 */
public class DESManager {

    // --- 以下的是 3des 加密和解密的方法
    private String desKey;
    private String desIv;

    private static DESManager instance = new DESManager();

    private DESManager() {
    }

    public String getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    public String getDesIv() {
        return desIv;
    }

    public void setDesIv(String desIv) {
        this.desIv = desIv;
    }

    /**
     * 获取单实例
     *
     * @return
     */
    public static DESManager getInstance() {
        return instance;
    }

    /**
     * 加密字符串，加密之后的字符串已经做了 Base64 算法，并做了 URL Encoder、
     * <p>
     * 1、对字符串进行 3DES 加密 2、然后进行 Base 64 加密 3、最后进行 URLEncoder
     *
     * @return String 加密之后的 Base64 字符串
     * @ReqParam source
     * @throw Exception
     */
    public String encrypt(String source) throws Exception {
        byte[] key = getKey();
        byte[] iv = getIv();
        String result = DES.encryptWithIV(key, iv, source);
        // return URLEncoder.encode(result);
        // TODO XIAQIULEI不能进行二次编码
        return result;
    }

    /**
     * 解密字符串
     * <p>
     * 1、对字符串进行 URLDecoder 2、然后进行 Base 64 解密 3、最后进行 3DES 解密
     *
     * @return
     * @ReqParam s
     * @throw Exception
     */
    public String decryptWithURLDecoder(String s) throws Exception {
        byte[] key = getKey();
        byte[] iv = getIv();
        @SuppressWarnings("deprecation")
        String result = URLDecoder.decode(s);
        byte[] resultByte = DES.decrypt(key, iv,
                Base64.getByteFromBASE64(result));
        result = new String(resultByte, "UTF-8");
        return result;
    }

    /**
     * 解密字符串
     * <p>
     * 1、对字符串进行 URLDecoder 2、然后进行 Base 64 解密 3、最后进行 3DES 解密
     *
     * @return
     * @ReqParam s
     * @throw Exception
     */
    public String decrypt(String s) throws Exception {
        byte[] key = getKey();
        byte[] iv = getIv();
        String result = null;
        byte[] resultByte = DES.decrypt(key, iv, Base64.getByteFromBASE64(s));
        result = new String(resultByte, "UTF-8");
        return result;
    }

    private byte[] getKey() {
        byte[] key = new byte[24];
        key = Base64.getByteFromBASE64(desKey);
        return key;
    }

    private byte[] getIv() {
        byte[] iv = new byte[8];
        iv = Base64.getByteFromBASE64(desIv);
        return iv;
    }

    /**
     * 生成IV
     *
     * @return Base64之后的IV
     * @ReqParam ivStr 源IV str
     */
    public String generateIv(String ivStr) {

        byte[] iv = new byte[8];
        for (int i = 0; i < ivStr.length(); i++) {
            char c = ivStr.charAt(i);
            // int b = Integer.parseInt(String.valueOf(c));
            iv[i] = (byte) (c);
        }
        Log.i("=====","iv size:" + iv.length);
        String ivBase64 = Base64.getBASE64FromByte(iv);
        Log.i("=====","iv base64 ： " + ivBase64);
        return ivBase64;

    }

    /**
     * 生成24位Key
     *
     * @return Bas64之后的Key
     * @ReqParam keyS 传入的字符串
     */
    public String generateKey(String keyS) {
        // 生成 123456789012345678901234 的 key
        // String keyS = "123456789012345678901234";
        byte[] key = new byte[24];
        for (int i = 0; i < keyS.length(); i++) {
            char c = keyS.charAt(i);
            // int b = Integer.parseInt(String.valueOf(c));
            key[i] = (byte) (c);
        }
        Log.i("=====","key size:" + key.length);
        String keyBase64 = Base64.getBASE64FromByte(key);
        Log.i("=====","key base64 ： " + keyBase64);
        return keyBase64;
    }

    // public static void main(String[] args) throws Exception {
    //
    // String keyS = "2d4bfe97609eb58df55fd3c9";
    // String keyS1 = "970fa0f04a61cb12f11d665c";
    // String account = "uaE5nwaeDZvBWsQ7iGzPrQ==";
    // String desKey = "NThiNWM2ZDlmZjQxOTYwNzZkZjFiZjEx";
    // String desIv = "Y2VkYTkwZDU=";
    // String ivStr = "d795f93b";
    // String source = "Som/jHaWh9oo652+RycdUw==";
    //
    // DesLog.log("加密和解密");
    // getInstance().setDesIv(getInstance().generateIv(ivStr));
    // getInstance().setDesKey(getInstance().generateKey(keyS));
    // String s = getInstance().encrypt("300000540");
    // DesLog.log(s + "\n");
    // DesLog.log("加密结果：" + URLDecoder.decode(s));
    // String sn = "kKl4KHgZT+htS5Set5TBM2x8bSHpJ0Ad";
    //
    // DesLog.log("解密结果：" + getInstance().decrypt( URLDecoder.decode(s))
    // );
    //
    // }

    public static void main(String[] args) throws Exception {

        // String token = ShtSecurityManager.DEFAULT_DES_KEY;
        //
        // String md5Token = Md5Util.strMD5(token);
        //
        // String desKey = md5Token.substring(0, 24);
        // String desIv = md5Token.substring(0, 8);
        //
        // DesLog.log("加密和解密");
        // getInstance().setDesIv(getInstance().generateIv(desIv));
        // getInstance().setDesKey(getInstance().generateKey(desKey));
        //
        // String s = getInstance().encrypt("name1111");
        //
        // DesLog.log("加密结果：" + s);
        // DesLog.log("加密结果：" + URLDecoder.decode(s));
        // DesLog.log("解密结果：" + getInstance().decrypt(URLDecoder.decode(s)));
        // DesLog.log(getInstance().decrypt("DE91/fjhdP14bmulObYBVw=="));
    }
}