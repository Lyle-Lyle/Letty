package com.letty.usercenter.dto.req;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户退出队伍请求体
 *
 */
@Data
public class TeamQuitReq implements Serializable {


    @Serial
    private static final long serialVersionUID = -3142527197499531297L;
    /**
     * id
     */
    private Long teamId;

}
