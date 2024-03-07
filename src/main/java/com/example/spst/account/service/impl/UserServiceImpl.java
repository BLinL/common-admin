package com.example.spst.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spst.account.dao.UserMapper;
import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.po.UserPO;
import com.example.spst.account.service.UserService;
import com.example.spst.common.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResultVO<UserDTO> createNewUser(UserDTO userDTO) {
        var user = new UserPO();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (save(user)) {
            return ResultVO.success(userDTO);
        }
        return ResultVO.fail("注册用户失败");
    }
}
