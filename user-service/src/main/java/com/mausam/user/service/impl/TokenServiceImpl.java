package com.mausam.user.service.impl;

import com.mausam.user.entity.JwtAuthenticationToken;
import com.mausam.user.entity.User;
import com.mausam.user.security.JwtGenerator;
import com.mausam.user.service.TokenService;
import com.mausam.user.util.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final JwtGenerator jwtGenerator;

    @Autowired
    public TokenServiceImpl(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public JwtAuthenticationToken generateToken(User user) {
        String token = jwtGenerator.generate(user);
        System.out.println("TOKEN ==== " + token);
        if (token != null) {
            return new JwtAuthenticationToken(SecurityConstant.TOKEN_PREFIX + token);
        }
        return new JwtAuthenticationToken("Invalid Credentials");
    }
}
