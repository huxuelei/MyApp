package com.dev.httplib.http.response;

public class FailResponse extends BaseResponse implements IResponseError {

	private static final long serialVersionUID = 1L;

	public static FailResponse NET_EX = new FailResponse(new RuntimeException(
			"net exception"));

	public static FailResponse JSON_EX = new FailResponse(new RuntimeException(
			"json exception"));

	public static FailResponse TOKEN_EX = new FailResponse(
			new RuntimeException("token exception"));

	public static FailResponse PWD_EX = new FailResponse(new RuntimeException(// 用户被锁定
			"pwd exception"));

	public Exception exception;

	public FailResponse() {
		this(new RuntimeException("unknow exception"));
	}

	public FailResponse(Exception e) {
		super();
		this.exception = e;
	}
}
