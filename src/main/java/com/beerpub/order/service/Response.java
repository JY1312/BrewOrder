package com.beerpub.order.service;

public class Response<T> {

    private T data;
    private boolean success;
    private String errorMessage;

    public static <K> Response<K> newSuccess(K data) {
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static Response<Void> newFail(String errorMessage) {
        Response<Void> response = new Response<>();
        response.setErrorMessage(errorMessage);
        response.setSuccess(false);
        return response;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
