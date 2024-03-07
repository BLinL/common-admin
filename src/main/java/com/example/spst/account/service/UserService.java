package com.example.spst.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.po.UserPO;
import com.example.spst.common.ResultVO;

public interface UserService extends IService<UserPO> {
    ResultVO<UserDTO> createNewUser(UserDTO userDTO);
}
