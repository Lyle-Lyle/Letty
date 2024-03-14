package com.letty.application.common.resp;

import com.letty.usercenter.common.ErrorCode;
import lombok.Data;

/**
 * 返回结果类
 * @param <T>
 */

@Data
public class BaseResponse<T> {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }


    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null, errorCode.getMessage());
    }

    public BaseResponse(ErrorCode errorCode, String message) {
        this(errorCode.getCode(),null, errorCode.getMessage());
    }
}
