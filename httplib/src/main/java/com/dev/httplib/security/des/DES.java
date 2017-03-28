package com.dev.httplib.security.des;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("TrulyRandom")
public class DES {

	/**
	 * @ReqParam key
	 * @ReqParam iv
	 * @ReqParam str
	 * @return
	 * @throws Exception
	 */
	public static String encryptWithIV(byte[] key, byte[] iv, String str)
			throws Exception {
		SecureRandom sr = new SecureRandom();
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec ips = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, ips, sr);

		byte[] bt = cipher.doFinal(str.getBytes("UTF-8"));
		return Base64.getBASE64FromByte(bt);

	}

	/**
	 * @ReqParam key
	 * @ReqParam bIV
	 * @ReqParam bCipherText
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] key, byte[] bIV, byte[] bCipherText)
			throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		IvParameterSpec iv = new IvParameterSpec(bIV);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		SecureRandom sr = new SecureRandom();
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv, sr);
		byte[] bOutput = cipher.doFinal(bCipherText);
		return bOutput;
	}

	public static void main(String[] args) {
		// TestDES();
		TestDes1();
	}

	static void TestDES() {
		try {
			byte[] key = new byte[24];
			for (int i = 0; i < key.length; i++) {
				key[i] = (byte) (i + 11);
			}

			byte[] iv = new byte[8];
			for (int i = 0; i < iv.length; i++) {
				iv[i] = (byte) (i + 1);
			}

			String s = "helloword";

			Log.i("=====","IV length : " + iv.length);
			Log.i("=====","key size:" + key.length);
			String keyBase64 = Base64.getBASE64FromByte(key);
			Log.i("=====","Key base64 ： " + keyBase64);
			Log.i("=====","IV base64 : " + Base64.getBASE64FromByte(iv));

			String keyString = "";
			if (keyBase64.length() >= 24) {
				keyString = keyBase64.substring(0, 24);
				key = keyString.getBytes();
			} else {
				StringBuffer tmp = new StringBuffer();
				for (int i = keyBase64.length(); i < 24; i++) {
					tmp.append("0");
				}
				key = (keyString + tmp.toString()).getBytes();
			}

			Log.i("=====","截取之后的Key ： " + new String(key));
			String result = encryptWithIV(key, iv, s); // 加密

			Log.i("=====","原文 ： " + s);
			Log.i("=====","密文 base64: " + result);

			byte[] decryptBytes = decrypt(key, iv,
					Base64.getByteFromBASE64(result));
			Log.i("=====","解密：" + new String(decryptBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void TestDes1() {
		try {
			String keyStr = "123456789012345678901234";
			// byte[] key = new byte[24];
			// byte []key = new byte[]
			// {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
			byte[] key = keyStr.getBytes();
			// byte []keyBytes = keyStr.getBytes();
			// for (int i = 0; i < key.length; i++) {
			// key[i] = (byte) (i + 11);
			// }
			String ivStr = "12345678";
			// byte[] iv = new byte[]{1,2,3,4,5,6,7,8};
			byte[] iv = HexUtils.strToByte(ivStr); // 转换成new
													// byte[]{1,2,3,4,5,6,7,8}
			// byte []iv = ivStr.getBytes();

			// for (int i = 0; i < iv.length; i++) {
			// iv[i] = (byte) (i + 1);
			// }

			String s = "abc";

			Log.i("=====","IV length : " + iv.length);
			Log.i("=====","key size:" + key.length);
			String keyBase64 = Base64.getBASE64FromByte(key);
			Log.i("=====","Key base64 ： " + keyBase64);
			Log.i("=====","IV base64 : " + Base64.getBASE64FromByte(iv));

			String keyString = "";
			if (keyBase64.length() >= 24) {
				keyString = keyBase64.substring(0, 24);
				key = keyString.getBytes();
			} else {
				StringBuffer tmp = new StringBuffer();
				for (int i = keyBase64.length(); i < 24; i++) {
					tmp.append("0");
				}
				key = (keyString + tmp.toString()).getBytes();
			}

			Log.i("=====","截取之后的Key ： " + new String(key));

			String result = DESManager.getInstance().encrypt(s); // 加密

			Log.i("=====","原文 ： " + s);
			Log.i("=====","密文 base64: " + result);

			byte[] decryptBytes = decrypt(key, iv,
					Base64.getByteFromBASE64(result));
			Log.i("=====","解密：" + new String(decryptBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String strMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
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
		return null;
	}
}
