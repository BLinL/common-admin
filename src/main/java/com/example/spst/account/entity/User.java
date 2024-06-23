package com.example.spst.account.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("sys_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String createBy;
    private String head;
    @TableField(exist = false)
    private List<String> roles;
    @TableField(exist = false)
    private List<Permission> permissons;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
