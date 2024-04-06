package com.example.spst.account.dao;

import com.example.spst.account.entity.User;
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
        User user = userMapper.selectById(-1);
        assertEquals(user, null);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("good");
        user.setPassword("bad");
        user.setEmail("101010@qq.com");

        User user1 = new User();
        user1.setUsername("good1");
        user1.setPassword("bad1");
        user1.setEmail("1010100@qq.com");

        int count = userMapper.insert(user);
        assertEquals(count, 1);
        count = userMapper.insert(user1);
        assertEquals(count, 1);
    }
}