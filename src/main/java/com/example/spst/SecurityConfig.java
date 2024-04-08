package com.example.spst;

import com.example.spst.secutiry.LoginFailureHandler;
import com.example.spst.secutiry.LoginSuccessHandler;
import com.example.spst.secutiry.filter.JWTAuthFilter;
import com.example.spst.secutiry.filter.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.util.AntPathMatcher;


@Configuration
public class SecurityConfig {

    @Autowired
    @Qualifier("myUserDetailService")
    MyUserDetailService myUserDetailService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Bean
    public LoginFilter loginFilter(ObjectMapper objectMapper, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler) {
        return new LoginFilter(authenticationManager(), objectMapper, loginFailureHandler, loginSuccessHandler);
    };
    /**
     * 定义比较密码的加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager使用UserDetailsService来加载用户信息进而进行认证AuthenticationManager#authenticate
     *
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        /**
         * 支持用户名密码认证
         * AbstractUserDetailsAuthenticationProvider的实现
         *
         * DaoAuthenticationProvider#additionalAuthenticationChecks 中使用加密算法匹配用户密码
         */
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        // 多数据源
        DaoAuthenticationProvider daoAuthenticationProvider1 = new DaoAuthenticationProvider();
        daoAuthenticationProvider1.setUserDetailsService(myUserDetailService);
        ProviderManager pm = new ProviderManager(daoAuthenticationProvider1);
        return pm;
    }

    /**
     * AuthenticationProvider认证管理器对传入的authentication对象进行认证
     * 默认是ProviderManager， 可以有多个， ProviderManager可以有parent ProviderManager
     * 认证后的Authentication对象放入SecurityContextHolder
     * 是实际认证处理的地方，可以自定义AuthenticationProvider 来进行功能扩展
     *
     * @return
     */


    /**
     * WebSecurity 是更高层的构建，负责把多个DefaultFilterChain构建为新的FilterChain
     * spring security 的 filter chain 并不是 原生的 filter chain
     * spring 提供DelegatingFilterProxy 将 FilterChainProxy对象(本质也是一个过滤器)嵌入到原生的过滤器链中
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, LoginFilter loginFilter) throws Exception {
        /**
         * HttpSecurity是 SecurityBuilder的实现 用于构建DefaultSecurityFilterChain对象
         * springSecurity 中所有需要构建的对象都可以通过 SecurityBuilder
         *
         */

        // 局部 为啥不起作用
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(User.withUsername("张三2").password("{bcrypt}$2a$10$UZN8DBdO45QXljuLTTCGVucvsUxpD7TOYCHcM3z7CCnm1F4nXJAJC").roles("user").build());
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(customer -> {
                    customer
                            .requestMatchers("/login")
                            .permitAll()
                            .requestMatchers("/api/user/**").hasRole("user")
                            .anyRequest().authenticated();
//                            .access((authenticationSupplier, requestAuthorizationContext) -> {
//                                Authentication authentication = authenticationSupplier.get();
////                                if (!authentication.isAuthenticated()) {
////                                    return new AuthorizationDecision(false);
////                                }
//                                String requestURI = requestAuthorizationContext.getRequest().getRequestURI();
//                                System.out.println("check perm: "  + requestURI);
//                                AntPathMatcher antPathMatcher = new AntPathMatcher();
//                                if (antPathMatcher.match("/p2", requestURI)) {
//                                    System.out.println("/p2");
//                                    return new AuthorizationDecision(false);
//                                }
//                                return new AuthorizationDecision(true);
//                            });
                })
                .addFilterBefore(new JWTAuthFilter(), LoginFilter.class)
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    //
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        // 忽略 /error 页面
        return web -> web.ignoring()
                .requestMatchers("/error")
                // 忽略常见的静态资源路径
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
