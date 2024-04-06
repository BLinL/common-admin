package com.example.spst.account.service.impl;

import com.example.spst.account.entity.Role;
import com.example.spst.account.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testAddRole() {
        var role = new Role();
        role.setRoleName("操作员2");
        roleService.save(role);
    }
}