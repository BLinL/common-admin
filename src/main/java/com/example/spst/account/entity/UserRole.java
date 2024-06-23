package com.example.spst.account.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class UserRole {

    @TableId
    private Integer id;
    @TableField(value = "u_id")
    private Integer uId;
    @TableField(value = "r_id")
    private Integer rId;
}
