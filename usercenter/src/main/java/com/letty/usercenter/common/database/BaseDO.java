package com.letty.usercenter.common.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BaseDO {

    @TableField(fill= FieldFill.INSERT)
    private Date createdTime;

    /**
     * updated_time
     */
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    /**
     * deletion flag 0 or 1
     */
    @TableField(fill= FieldFill.INSERT)
    @TableLogic
    private Integer isDeleted;
}
