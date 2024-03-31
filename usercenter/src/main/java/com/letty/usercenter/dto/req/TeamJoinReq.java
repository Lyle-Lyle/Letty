package com.letty.usercenter.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户加入队伍请求体
 *
 */
@Data
public class TeamJoinReq implements Serializable {


    @Serial
    private static final long serialVersionUID = 5163170874837506614L;
    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
