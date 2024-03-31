package com.letty.usercenter.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用分页参数
 */

@Data
public class PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1071137214882012191L;
    protected int pageSize = 10;
    protected int pageNum = 1;
}
