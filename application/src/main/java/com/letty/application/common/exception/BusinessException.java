package com.letty.application.common.exception;

import com.letty.usercenter.common.ErrorCode;

/**
 * 自定义业务异常类
 *
 */
public class BusinessException extends RuntimeException{

    private final int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
