package com.mausam.user.security;

import com.mausam.user.entity.User;
import com.mausam.user.util.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public User validate(String token) {

        User jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new User();

            jwtUser.setEmail((String) body.get("email"));
            jwtUser.setUserId(Long.parseLong((String) body.get("userId")));
            jwtUser.setRole((String) body.get("role"));


        } catch (Exception e) {
            System.out.println("Error in validating Token");
        }

        return jwtUser;
    }
}