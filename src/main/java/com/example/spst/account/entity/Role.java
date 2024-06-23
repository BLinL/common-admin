package com.example.spst.account.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("sys_role")
public class Role {

    @TableId(type = IdType.AUTO)
    private Long roleId;
    private String roleName;
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(exist = false)
    private List<Permission> permissionList;
}
