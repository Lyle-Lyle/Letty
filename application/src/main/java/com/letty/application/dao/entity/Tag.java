package com.letty.application.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.letty.application.common.database.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Tag extends BaseDO {


    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标签名称
     */
    private String tagName;

    /**
     * user who created this tag
     */
    private Long userId;

    /**
     * 父标签id
     */
    private Long parentId;

    /**
     * 0 - 不是，1 - 父标签
     */
    private Integer isParent;
}