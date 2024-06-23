package com.example.spst.account.dto;

import com.example.spst.account.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String head;
    private String token;
    private String createBy;
    private List<Role> roleList;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
}
