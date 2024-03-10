package com.example.spst.account.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user")
public class UserPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date insertTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
