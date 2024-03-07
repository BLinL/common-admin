package com.example.spst.account.service.impl;

import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.service.UserService;
import com.example.spst.common.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("");
        userDTO.setUsername("zs");
        userDTO.setPassword("123");

        Method[] declaredMethods = userService.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }

        ResultVO<UserDTO> user = userService.createNewUser(userDTO);
        assertEquals(user.getCode(), 0);
    }
}