package com.shaoqunliu.demo.estore.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/v1/user/token/");
        setPostOnly(true);
    }

    private void authenticationInfoPrinter(HttpServletRequest request, HttpServletResponse response, RestfulResult result) throws IOException {
        if (request.getHeader("Accept").toLowerCase().contains("application/xml")) {
            response.setHeader("Content-Type", "application/xml");
            response.getWriter().print(result.toXmlString());
        } else if (request.getHeader("Accept").toLowerCase().contains("application/json")) {
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(result.toJsonString());
        } else {
            throw new IOException("Unsupported HTTP Accept header");
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            RBACUser user = new ObjectMapper().readValue(req.getInputStream(), RBACUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getId().toString(),
                            user.getPassword()));
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((UserDetails) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "fuck yeah!")
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
        response.setStatus(HttpStatus.CREATED.value());
        HashMap<String, Object> auth = new HashMap<>();
        auth.put("auth", ((UserDetails) authResult.getPrincipal()).getAuthorities());
        RestfulResult result = new RestfulResult(0, "Login successfully", auth);
        authenticationInfoPrinter(request, response, result);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        RestfulResult result = new RestfulResult(1, failed.getMessage(), new HashMap<>());
        authenticationInfoPrinter(request, response, result);
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
