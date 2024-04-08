package com.example.spst.secutiry.filter;

import com.example.spst.MyUserDetailService;
import com.example.spst.common.ResultVO;
import com.example.spst.secutiry.LoginFailureHandler;
import com.example.spst.secutiry.LoginSuccessHandler;
import com.example.spst.secutiry.filter.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * UsernamePasswordAuthenticationFilter是 AbstractAuthenticationProcessingFilter
 * 的实现类
 * UsernamePasswordAuthenticationFilter 提供 UsernamePasswordAuthenticationToken 交给 ProviderManager处理
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper;
    private LoginFailureHandler loginFailureHandler;
    private LoginSuccessHandler loginSuccessHandler;

    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, LoginFailureHandler loginFailureHandler, LoginSuccessHandler loginSuccessHandler) {
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(loginFailureHandler);
        setAuthenticationSuccessHandler(loginSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Map<String, String> map = new HashMap<>();
        try {
            map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String username = map.get("username");
        String password = map.get("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }

    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("张三1").password("{bcrypt}$2a$10$UZN8DBdO45QXljuLTTCGVucvsUxpD7TOYCHcM3z7CCnm1F4nXJAJC").roles("user").build());
        return userDetailsManager;
    }



//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
//
//    }
}
