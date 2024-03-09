package com.example.spst;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spst.account.po.UserPO;
import com.example.spst.account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPO user = userService.getOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("username or password is error");
        }
        User userDetails = new User(user.getUsername(), user.getPassword(), Set.of());
        return userDetails;
    }
}
