package com.example.spst.secutiry;

import com.example.spst.common.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理授权失败
 */
@Component
public class CustomerAccessDenyHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper customObjectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultVO<Object> authenticationFailed = ResultVO.fail("Authentication failed", "401");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(customObjectMapper.writeValueAsString(authenticationFailed));
    }
}
