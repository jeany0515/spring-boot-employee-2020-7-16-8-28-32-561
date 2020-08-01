package com.thoughtworks.springbootemployee.entity;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = 0;
    public static final String SUCCESS = "success";
    private int code;
    private String message;
    private T data;

    private ResultBean() {

    }

    public static <T> ResultBean<T> error(int code, String message) {
        ResultBean<T> resultBean = new ResultBean<>();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        return resultBean;
    }

    public static <T> ResultBean<T> success() {
        ResultBean<T> resultBean = new ResultBean<>();
        resultBean.setCode(SUCCESS_CODE);
        resultBean.setMessage(SUCCESS);
        return resultBean;
    }

    public static <T> ResultBean<T> success(T data) {
        ResultBean<T> resultBean = new ResultBean<>();
        resultBean.setCode(SUCCESS_CODE);
        resultBean.setMessage(SUCCESS);
        resultBean.setData(data);
        return resultBean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}