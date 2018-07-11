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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final PermissionService permissionService;

    @Autowired
    public SpringSecurityConfiguration(UserService userService, BCryptPasswordEncoder encoder, PermissionService permissionService) {
        this.userService = userService;
        this.encoder = encoder;
        this.permissionService = permissionService;
    }

    @Bean
    public MySecurityMetadataSource mySecurityMetadataSource() {
        return new MySecurityMetadataSource(permissionService);
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");
        configuration.setAllowCredentials(true);
        return configuration;
    }

    @Bean
    public UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        Map<String, CorsConfiguration> configurationMap = new HashMap<>();
        configurationMap.put("/**", corsConfiguration());
        source.setCorsConfigurations(configurationMap);
        return source;
    }

    public SecurityFilter securityFilter() {
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setSecurityMetadataSource(mySecurityMetadataSource());
        securityFilter.setAccessDecisionManager(new MyAccessDecisionManager());
        return securityFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(urlBasedCorsConfigurationSource())
                .and()
                .csrf().disable()
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
                .passwordEncoder(encoder);
    }
}
