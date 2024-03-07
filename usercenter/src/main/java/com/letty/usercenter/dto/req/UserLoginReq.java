package com.letty.usercenter.dto.req;

import lombok.Data;

/**
 * user register request params
 */
@Data
public class UserLoginReq {
    private String userAccount;
    private String userPassword;
}
