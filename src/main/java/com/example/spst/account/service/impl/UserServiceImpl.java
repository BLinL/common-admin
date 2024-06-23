package com.example.spst.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spst.account.dao.PermissionMapper;
import com.example.spst.account.dao.RolePermissonMapper;
import com.example.spst.account.dao.UserMapper;
import com.example.spst.account.dao.UserRoleMapper;
import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.entity.Permission;
import com.example.spst.account.entity.RolePermission;
import com.example.spst.account.entity.User;
import com.example.spst.account.entity.UserRole;
import com.example.spst.account.service.UserService;
import com.example.spst.common.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserRoleMapper userRoleMapper;
    private final RolePermissonMapper rolePermissonMapper;
    private final PermissionMapper permissionMapper;

    @Override
    public ResultVO<UserDTO> createNewUser(UserDTO userDTO) {
        User user = toUser(userDTO);
        if (save(user)) {
            return ResultVO.success(userDTO);
        }
        return ResultVO.fail("注册用户失败");
    }

    @Override
    public UserDTO toUserDTO(User user) {
        var userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public ResultVO<List<UserDTO>> listUser() {
        List<User> list = list();
        List<UserDTO> collect = list.stream().map(this::toUserDTO).collect(Collectors.toList());
        return ResultVO.success(collect);
    }

    @Override
    public ResultVO<Void> updateUser(UserDTO userDTO) throws IllegalAccessException {
        if (userDTO.getId() == null) {
            throw new IllegalAccessException("id is null");
        }

        boolean b = updateById(toUser(userDTO));
        if (!b) {
            throw new RuntimeException("操作失败，请联系管理员");
        }
        return ResultVO.success(null);
    }

    @Override
    public User queryUserByUsername(String username) {
        var user = getOne(new QueryWrapper<User>().eq("username", username));
        List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUId, user.getId()));

        if (userRoles.size() > 0) {
            List<RolePermission> rolePermissons = rolePermissonMapper.selectList(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRId, userRoles.stream()
                    .map(UserRole::getRId).collect(Collectors.toList())));

            if (rolePermissons.size() > 0) {
                List<Integer> pids = rolePermissons.stream().map(RolePermission::getPId).collect(Collectors.toList());

                if (pids.size() > 0) {
                    List<Permission> permissions = permissionMapper.selectList(new LambdaQueryWrapper<Permission>().in(Permission::getPermissionId, pids));
                    user.setPermissons(permissions);
                }
            }

        }



        user.setPassword(null);
        return user;
    }

    public User toUser(UserDTO userDTO) {
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }
}
