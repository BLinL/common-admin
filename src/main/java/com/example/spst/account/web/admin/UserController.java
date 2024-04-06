package com.example.spst.account.web.admin;


import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.service.UserService;
import com.example.spst.common.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {


    private final UserService userService;

    @GetMapping("list")
    public ResultVO<List<UserDTO>> listUser() {
        return userService.listUser();
    }

    @GetMapping("update")
    public ResultVO<Void> updateUser(@RequestBody UserDTO userDTO) throws IllegalAccessException {
        return userService.updateUser(userDTO);
    }


}
