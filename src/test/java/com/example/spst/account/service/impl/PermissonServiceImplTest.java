package com.example.spst.account.service.impl;

import com.example.spst.account.po.PermissionPO;
import com.example.spst.account.service.PermissonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PermissonServiceImplTest {

    @Autowired
    private PermissonService permissonService;

    @Test
    public void testAddPermisson() {

        var perm = new PermissionPO();
        perm.setName("用户管理");
        perm.setUrl("/api/manager/user");
        permissonService.save(perm);
    }
}