package com.app.url_shortener.controller;

import com.app.url_shortener.dto.AuthResponseDto;
import com.app.url_shortener.dto.UserLogInDto;
import com.app.url_shortener.dto.UserRegisterRequestDto;
import com.app.url_shortener.dto.UserResponseDto;
import com.app.url_shortener.entities.User;
import com.app.url_shortener.service.EntityDtoMapper;
import com.app.url_shortener.security.JwtService;
import com.app.url_shortener.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final EntityDtoMapper entityDtoMapper;
    private final JwtService jwtService;

    public AuthController(UserService userService,EntityDtoMapper entityDtoMapper,JwtService jwtService){
        this.userService=userService;
        this.entityDtoMapper=entityDtoMapper;
        this.jwtService=jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegisterRequestDto registerRequestDto){
        User user = userService.register(registerRequestDto);
        return ResponseEntity.ok(entityDtoMapper.toUserDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLogInDto logInDto){
        User user = userService.login(logInDto);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
