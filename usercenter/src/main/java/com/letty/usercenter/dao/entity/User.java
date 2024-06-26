package com.letty.usercenter.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.letty.usercenter.common.database.BaseDO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class User extends BaseDO implements Serializable {


    @Serial
    private static final long serialVersionUID = 6355381290190518926L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0 - 正常
     */
    private Integer userStatus;
    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    private Integer userRole;

}