package com.example.spst.secutiry;

import com.example.spst.common.ResultVO;
import com.example.spst.secutiry.filter.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper;

    public LoginSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("roles", new ArrayList<>());
        claims.put("permissions", new ArrayList<>());
        String token = JwtService.createToken(claims, userDetails.getUsername());

        response.setContentType("application/json;charset=utf-8");
        ResultVO<Map<String, String>> resultVO = ResultVO.success("", Map.of("token", token));
        response.getWriter().write(objectMapper.writeValueAsString(resultVO));
    }
}
