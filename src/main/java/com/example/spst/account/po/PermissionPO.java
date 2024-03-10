package com.example.spst.account.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_permission")
public class PermissionPO {
    @TableId(type = IdType.AUTO)
    private Long permissionId;
    private String url;
    private String name;

    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
