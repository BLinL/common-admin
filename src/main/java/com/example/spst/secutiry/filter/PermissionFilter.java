package com.example.spst.secutiry.filter;

import com.example.spst.secutiry.UserPrinciple;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class PermissionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserPrinciple principal = (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        doFilter(request, response, filterChain);
//        if (authorities != null) {
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(request.getRequestURI())) {
//                    doFilter(request, response, filterChain);
//                    return;
//                }
//            }
//        }

//        throw new AccessDeniedException(" Unauthorized");
    }
}
