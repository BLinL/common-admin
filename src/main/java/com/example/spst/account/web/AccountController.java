package com.example.spst.account.web;


import com.example.spst.account.dto.UserDTO;
import com.example.spst.account.service.UserService;
import com.example.spst.common.ResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final UserService userService;

    @PostMapping("/create")
    public ResultVO<UserDTO> createUser(UserDTO userDTO) {
        return userService.createNewUser(userDTO);
    }
}
