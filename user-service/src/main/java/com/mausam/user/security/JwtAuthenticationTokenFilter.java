package com.mausam.user.security;

import com.mausam.user.entity.JwtAuthenticationToken;
import com.mausam.user.util.SecurityConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationTokenFilter() {
        super("/*/rest/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {

        String header = httpServletRequest.getHeader(SecurityConstant.HEADER_STRING);
        //System.out.println("ReactJs = "+header);
        if (header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            throw new RuntimeException("JWT Token is missing");
        }

        String authenticationToken = header.substring(SecurityConstant.TOKEN_PREFIX.length());

        JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
        return getAuthenticationManager().authenticate(token);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}