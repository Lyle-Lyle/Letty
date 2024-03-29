package com.letty.usercenter.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.letty.usercenter.common.database.BaseDO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class Team extends BaseDO implements Serializable{


    @Serial
    private static final long serialVersionUID = -8324142334602647566L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 公开 1 私有 2 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;
}