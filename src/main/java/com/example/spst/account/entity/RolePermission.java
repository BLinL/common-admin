package com.example.spst.account.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role_perm")
public class RolePermission {

    @TableId
    private Integer id;
    @TableField("r_id")
    private Integer rId;
    @TableField("p_id")
    private Integer pId;
}
