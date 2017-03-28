package com.dev.httplib.security.md5;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA. User: nanzhiwen Date: 13-6-7 Time: 上午11:51 MD5
 * 加密算法工具类
 */
public class Md5Util {

	/**
	 * 将字符串Md5 加密并转换成32位小写
	 * 
	 * @ReqParam s
	 * @return
	 */
	public static final String strMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
