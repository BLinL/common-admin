package com.example.spst.secutiry.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher = (request) -> !request.getMethod().equalsIgnoreCase("option")
            && !(request.getRequestURI().equals("/login") || request.getRequestURI().equals("/logout"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestMatcher.MatchResult result = requestMatcher.matcher(request);

        if (result.isMatch()) {
            String token1 = request.getHeader("Authorization");
            if (token1 == null) {
                filterChain.doFilter(request, response);
                return;
            }
            try {
                String token = token1.substring(7);
                UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)JwtService.getAuthentication(token);
                Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
                if(authentication.getPrincipal() != null && authentication1 == null) {
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("--> {}", request.getRequestURI());
                }
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                log.error("authencation has error:",e);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"code\": 50012, msg:\"认证失败\" }");
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
