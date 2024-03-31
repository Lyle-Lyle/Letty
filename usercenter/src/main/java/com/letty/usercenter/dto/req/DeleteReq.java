package com.letty.usercenter.dto.req;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用删除请求
 *
 */
@Data
public class DeleteReq implements Serializable {


    @Serial
    private static final long serialVersionUID = -1274788910918483513L;
    private long id;
}
