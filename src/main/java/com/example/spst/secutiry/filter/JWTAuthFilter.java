package com.example.spst.secutiry.filter;

import com.example.spst.account.entity.Permission;
import com.example.spst.account.service.UserService;
import com.example.spst.secutiry.UserPrinciple;
import com.example.spst.secutiry.UserPrincipleAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
//    private RequestMatcher requestMatcher = (request) -> !request.getMethod().equalsIgnoreCase("option")
//            && !(request.getRequestURI().equals("/login"));

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // RequestMatcher.MatchResult result = requestMatcher.matcher(request);

        //if (result.isMatch()) {
        String authorizationToken = request.getHeader("Authorization");
        if (authorizationToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationToken.substring(7);
        UserPrincipleAuthenticationToken authentication = (UserPrincipleAuthenticationToken) JwtService.getAuthentication(token);
        if (authentication != null) {
            var username = ((UserPrinciple) authentication.getPrincipal()).getUsername();
            var user = userService.queryUserByUsername(username);
            if (user.getPermissons() != null) {
                ((UserPrinciple) authentication.getPrincipal()).getAuthorities().addAll(
                        user.getPermissons().stream().map(p -> new SimpleGrantedAuthority(p.getPath())).toList()
                );
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            throw new AuthenticationServiceException("no token found");
        }
//                Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
//                if(authentication.getPrincipal() != null && authentication1 == null) {
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    log.info("--> {}", request.getRequestURI());
//                }
//            try {
//
//            } catch (Exception e) {
//                log.error("authencation has error:",e);
//                response.setContentType("application/json;charset=utf-8");
//                response.getWriter().write("{\"code\": 50012, msg:\"认证失败\" }");
//            }

//        } else {
//            filterChain.doFilter(request, response);
//        }
    }
}
