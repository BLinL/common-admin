package com.example.spst.account.dao;

import com.example.spst.account.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void smoke_test() {
        UserPO userPO = userMapper.selectById(-1);
        assertEquals(userPO, null);
    }

    @Test
    public void testInsert() {
        UserPO userPO = new UserPO();
        userPO.setUsername("good");
        userPO.setPassword("bad");
        userPO.setEmail("101010@qq.com");

        UserPO userPO1 = new UserPO();
        userPO1.setUsername("good1");
        userPO1.setPassword("bad1");
        userPO1.setEmail("1010100@qq.com");

        int count = userMapper.insert(userPO);
        assertEquals(count, 1);
        count = userMapper.insert(userPO1);
        assertEquals(count, 1);
    }
}