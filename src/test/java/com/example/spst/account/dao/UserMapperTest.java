package com.example.spst.account.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.spst.account.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void smoke_test() {
        User user = userMapper.selectById(-1);
        assertEquals(user, null);
    }

    @Test
    @Rollback
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


    @Test
    public void testQueryByLambda() {

        // selec * from user where username = 'good' and (email = '' or id > 11) or updateTime > '2022-01-01 00:00:00'
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, "good")
                .and((wrapper) -> {
                    wrapper.eq(User::getEmail, 10).or(wrapper1 -> wrapper1.gt(User::getId, 11));
                }).or(w -> w.gt(User::getUpdateTime, "2022-01-01 00:00:00"));
        System.out.println(queryWrapper.getTargetSql());
    }

    @Test
    public void testSelectCount() {
        Long aLong = userMapper.selectCount(new QueryWrapper<>());
        System.out.println(aLong);

        long l = SqlHelper.retCount(userMapper.selectCount(new QueryWrapper<>()));
        System.out.println(l);

        System.out.println(SqlHelper.table(User.class));

    }

    @Test
    public void testUpdateUser() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());

        for (User user : users) {
            if(user != null) {
                user.setEmail(
                        "111@qq.com"
                );

                int i = userMapper.updateById(user);
                assertEquals(1 , i);
            }
        }
    }

    @Test
    public void testUpdateUserByWrapper() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());

        for (User user : users) {
            if(user != null) {

//                User toUpdate = new User();
//                toUpdate.setEmail(  "111@qq.com");
//                LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
//                        .eq(User::getId, user.getId());
//                int i = userMapper.update(toUpdate, updateWrapper);

                LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                        .eq(User::getId, user.getId())
                        .set(User::getEmail, "111@qq.com");

                int i = userMapper.update(updateWrapper);
                assertEquals(1 , i);
            }
        }
    }
}