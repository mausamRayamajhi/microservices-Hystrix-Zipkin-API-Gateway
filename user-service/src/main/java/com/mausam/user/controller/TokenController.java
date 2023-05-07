package com.mausam.user.controller;


import com.mausam.user.entity.User;
import com.mausam.user.service.TokenService;
import com.mausam.user.util.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API.TOKEN)
@CrossOrigin
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<String> generate(@RequestBody final User user) {
        return new ResponseEntity<>(tokenService.generateToken(user).getToken(), HttpStatus.OK);
    }
}
