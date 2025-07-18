package com.portafolio.ecommerce_api.controllers;

import com.portafolio.ecommerce_api.dto.RegisterDto;
import com.portafolio.ecommerce_api.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.portafolio.ecommerce_api.dto.LoginDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return new ResponseEntity<>("Usuario registrado exitosamente!", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return new ResponseEntity<>("Bearer " + token, HttpStatus.OK);
    }
}