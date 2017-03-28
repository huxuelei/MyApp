package com.dev.httplib.http.response;

import com.dev.httplib.http.Codes;
import com.google.gson.JsonElement;

public class JsonStrResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	public static final String JSON_DATA = "data";

	/**
	 * json状态码
	 * 
	 * @see Codes.RespCode
	 */
	public String code;

	/**
	 * 提示信息
	 */
	public String error;

	/**
	 * json data
	 */
	// public String dataStr;

	/**
	 * json data
	 */
	public JsonElement data;

	String jsonStr;

	public boolean isSuccess() {
		return Codes.RespCode.SUCCESS.equals(code);
	}

	public boolean isTokenInvalide() {
		return Codes.RespCode.TOKEN_INVALIDE.equals(code.trim())
				|| Codes.RespCode.TOKEN_INVALIDE2.equals(code.trim());
	}

	public boolean isPwdInvalide() {// 用户被锁定
		return Codes.RespCode.PWD_INVALIDE.equals(code.trim());
	}

	public String getJsonString() {
		if (jsonStr == null && data != null) {
			jsonStr = data.toString();
		}
		return jsonStr;
	}

	public String toString() {
		return "JsonStrResponse [code=" + code + ", error=" + error + ", data="
				+ data + "]";
	}
}