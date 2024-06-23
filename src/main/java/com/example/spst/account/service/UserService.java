package com.example.spst.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.entity.User;
import com.example.spst.common.ResultVO;

import java.util.List;

public interface UserService extends IService<User> {
    ResultVO<UserDTO> createNewUser(UserDTO userDTO);

    UserDTO toUserDTO(User user);

    ResultVO<List<UserDTO>> listUser();

    ResultVO<Void> updateUser(UserDTO userDTO) throws IllegalAccessException;

    User queryUserByUsername(String username);
}
