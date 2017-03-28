package com.dev.httplib.http.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class JsonObjResponse<I extends IJsonObj> extends BaseResponse {

	private static final long serialVersionUID = 1L;

	/**
	 * json状态码
	 */
	public String code;

	/**
	 * 提示信息
	 */
	public String error;

	/**
	 * json data
	 */
	@SerializedName("data")
	public I jsonObj;

	public boolean isSuccess() {
		return "0".equals(code);
	}

	public String toString() {
		return "JsonObjResponse [code=" + code + ", error=" + error + ", jsonObj=" + jsonObj + "]";
	}

	/**
	 * @ReqParam cls
	 * @return
	 */
	public static <T extends IJsonObj> JsonObjResponse<T> newInstance(Class<T> cls, JsonStrResponse response) {
		JsonObjResponse<T> jsonResponse = new JsonObjResponse<T>();
		jsonResponse.json = response.json;
		jsonResponse.code = response.code;
		jsonResponse.error = response.error;
		jsonResponse.respId = response.respId;
		jsonResponse.json = response.json;
		jsonResponse.jsonObj = new Gson().fromJson(response.getJsonString(), cls);
		return jsonResponse;
	}
}