package com.example.spst.account.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long permissionId;
    private Long parentId;
    private String path;
    private String permName;
    private String permType;
    private String icon;
    private String component;
    private String perm;
    private Integer sort;

    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
