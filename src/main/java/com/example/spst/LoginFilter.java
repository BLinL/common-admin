package com.example.spst;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *UsernamePasswordAuthenticationFilter是 AbstractAuthenticationProcessingFilter
 * 的实现类
 * UsernamePasswordAuthenticationFilter 提供 UsernamePasswordAuthenticationToken 交给 ProviderManager处理
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

//    private PasswordEncoder passwordEncoder;
//
//    public LoginFilter(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("authentication method is not supported:"
                    + request.getMethod());
        }

        Map<String, String> map = new HashMap<>();
        try {
            map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String username = map.get("username");
        String password = map.get("password");

        // 密码加密存储时不需要手动对前端密码进行加密
        // delegatePasswordEncoder中会找到加密使用的具体方式，在使用加密方式中提供的match方法进行比较
        // 对前端密码进行加密
//        String encodedPassword = passwordEncoder.encode(password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }
}
