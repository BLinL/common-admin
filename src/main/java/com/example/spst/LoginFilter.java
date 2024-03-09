package com.example.spst;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.*;

/**
 * UsernamePasswordAuthenticationFilter是 AbstractAuthenticationProcessingFilter
 * 的实现类
 * UsernamePasswordAuthenticationFilter 提供 UsernamePasswordAuthenticationToken 交给 ProviderManager处理
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

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

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();

        // 在这里生成令牌，你可以使用任何适合你的方法
        String token = generateToken(userDetails);

        // 将令牌写入响应头
        response.addHeader("Authorization", "Bearer " + token);
    }

    private String generateToken(UserDetails userDetails) {
        final String id = UUID.randomUUID().toString();

        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(generalKey())
                .id(id)
                .issuer("me")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (10 * 1000 * 60)))
                .claim("username", userDetails.getUsername());
        return jwtBuilder.compact();
    }

    public static SecretKey generalKey(){
        return Jwts.SIG.HS256.key().build();
    }
}
