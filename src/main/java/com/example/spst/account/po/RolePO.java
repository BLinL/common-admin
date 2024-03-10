package com.example.spst.account.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_role")
public class RolePO {

    @TableId(type = IdType.AUTO)
    private Long roleId;
    private String roleName;
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
