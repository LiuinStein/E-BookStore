package com.shaoqunliu.demo.estore.configuration;

import com.shaoqunliu.demo.estore.security.access.MyAccessDecisionManager;
import com.shaoqunliu.demo.estore.security.access.MyAuthenticationEntryPoint;
import com.shaoqunliu.demo.estore.security.access.MySecurityMetadataSource;
import com.shaoqunliu.demo.estore.security.filter.JWTAuthenticationFilter;
import com.shaoqunliu.demo.estore.security.filter.JWTLoginFilter;
import com.shaoqunliu.demo.estore.security.filter.SecurityFilter;
import com.shaoqunliu.demo.estore.service.PermissionService;
import com.shaoqunliu.demo.estore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PermissionService permissionService;

    @Autowired
    public SpringSecurityConfiguration(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public MySecurityMetadataSource mySecurityMetadataSource() {
        return new MySecurityMetadataSource(permissionService);
    }

    public SecurityFilter securityFilter() {
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setSecurityMetadataSource(mySecurityMetadataSource());
        securityFilter.setAccessDecisionManager(new MyAccessDecisionManager());
        return securityFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .httpBasic().authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/user/token/").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(securityFilter(), FilterSecurityInterceptor.class)
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
