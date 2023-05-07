package com.mausam.user.security;


import com.mausam.user.entity.JwtAuthenticationToken;
import com.mausam.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final JwtValidator validator;

    @Autowired
    public JwtAuthenticationProvider(JwtValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //System.out.println("2  = " + userDetails.getUsername());
        //System.out.println("After token verification = " + userDetails.toString());
        //System.out.println("TOKEN = "+((JwtAuthenticationToken) usernamePasswordAuthenticationToken).getToken());
    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        System.out.println("1 = In token verification ");
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();
        System.out.println("TOken = " + token);
        User jwtUser = validator.validate(token);

        if (jwtUser == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }
        System.out.println("user = " + jwtUser.toString());
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.getRole());

        User newUser = new User();
        newUser.setEmail(jwtUser.getEmail());
        newUser.setToken(token);
        newUser.setUserId(jwtUser.getUserId());
        newUser.setAuthorities(grantedAuthorities);
        return newUser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}