package com.letty.usercenter.common.exception;

import com.letty.usercenter.common.ErrorCode;

/**
 * 自定义业务异常类
 *
 */
public class BusinessException extends RuntimeException{

    private final int code;
    private final String desc;

    public BusinessException(String message, int code, String desc) {
        super(message);
        this.code = code;
        this.desc = desc;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
    }
    public BusinessException(ErrorCode errorCode, String desc) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
