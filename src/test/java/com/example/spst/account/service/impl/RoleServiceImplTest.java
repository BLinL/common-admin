package com.example.spst.account.service.impl;

import com.example.spst.account.po.RolePO;
import com.example.spst.account.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testAddRole() {
        var role = new RolePO();
        role.setRoleName("操作员2");
        roleService.save(role);
    }
}