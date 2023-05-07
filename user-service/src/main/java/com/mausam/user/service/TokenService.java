package com.mausam.user.service;


import com.mausam.user.entity.JwtAuthenticationToken;
import com.mausam.user.entity.User;

public interface TokenService {
    JwtAuthenticationToken generateToken(User user);
}
