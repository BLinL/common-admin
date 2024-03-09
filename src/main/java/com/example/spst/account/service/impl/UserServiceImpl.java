package com.example.spst.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spst.account.dao.UserMapper;
import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.po.UserPO;
import com.example.spst.account.service.UserService;
import com.example.spst.common.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public ResultVO<UserDTO> createNewUser(UserDTO userDTO) {
        UserPO userPO = toUserPO(userDTO);
        if (save(userPO)) {
            return ResultVO.success(userDTO);
        }
        return ResultVO.fail("注册用户失败");
    }

    private UserPO toUserPO(UserDTO userDTO) {
        var user = new UserPO();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }
}
