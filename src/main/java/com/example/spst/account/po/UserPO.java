package com.example.spst.account.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class UserPO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String email;
    private String password;
}
