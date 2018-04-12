package com.hsf1002.sky.wanandroid.core.http.exception;

/**
 * Created by hefeng on 18-4-11.
 */

public class ServerException extends Exception {
    private int code;

    public ServerException(int code) {
        this.code = code;
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
