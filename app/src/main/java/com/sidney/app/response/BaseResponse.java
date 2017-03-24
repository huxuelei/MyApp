package com.sidney.app.response;

import java.io.Serializable;

/**
 * Desction:
 */
public class BaseResponse<T> implements Serializable {
    private int status;
    private String detail;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
