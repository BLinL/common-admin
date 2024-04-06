package com.example.spst.account.service.impl;

import com.example.spst.account.entity.Permission;
import com.example.spst.account.service.PermissonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PermissonServiceImplTest {

    @Autowired
    private PermissonService permissonService;

    @Test
    public void testAddPermisson() {

        var perm = new Permission();
        perm.setPermName("用户管理");
        perm.setPath("/api/manager/user");
        permissonService.save(perm);
    }
}