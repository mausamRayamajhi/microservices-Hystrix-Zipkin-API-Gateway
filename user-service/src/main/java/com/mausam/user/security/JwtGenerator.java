package com.mausam.user.security;

import com.mausam.user.entity.User;
import com.mausam.user.service.UserService;
import com.mausam.user.util.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class JwtGenerator {

    private final UserService userService;

    @Autowired
    public JwtGenerator(UserService userService) {
        this.userService = userService;
    }

    public String generate(User jwtUser) {

        User activeUser;

        activeUser = userService.findByEmailAndStatus(jwtUser.getEmail(), 1);

        if (activeUser.getEmail() != null && activeUser.getPassword() != null
                && activeUser.getEmail().equals(jwtUser.getEmail())
                && new BCryptPasswordEncoder().matches(jwtUser.getPassword(), activeUser.getPassword())) {

            Claims claims = Jwts.claims();
            claims.put("userId", String.valueOf(activeUser.getUserId()));
            claims.put("email", String.valueOf(jwtUser.getEmail()));
            claims.put("role", activeUser.getRole());

            ZonedDateTime expireTime = ZonedDateTime.now(ZoneOffset.UTC).plus(SecurityConstant.EXPIRATION_TIME, ChronoUnit.MILLIS);

            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(Date.from(expireTime.toInstant()))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET).compact();
        }
        return null;

    }
}