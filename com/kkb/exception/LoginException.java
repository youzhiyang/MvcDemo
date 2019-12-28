package com.kkb.exception;

/**
 * 登入异常类
 */
public class LoginException extends Exception{

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
