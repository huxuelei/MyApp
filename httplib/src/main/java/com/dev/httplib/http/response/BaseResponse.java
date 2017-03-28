package com.dev.httplib.http.response;

public class BaseResponse implements IResponse {

	private static final long serialVersionUID = 1L;

	public String respId;

	public String json;

	public String toString() {
		return "BaseResponse [respId=" + respId + ", json=" + json + "]";
	}
}