package com.example.spst.secutiry.filter;

import com.example.spst.secutiry.filter.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        if(!request.getMethod().equals("POST")) {
//            throw new AuthenticationServiceException("authentication method is not supported:"
//                    + request.getMethod());
//        }

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

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("roles", new ArrayList<>());
        claims.put("permissions", new ArrayList<>());
        String token = JwtService.createToken(claims, userDetails.getUsername());
        // 将令牌写入响应头
        response.addHeader("Authorization", "Bearer " + token);
    }
}
