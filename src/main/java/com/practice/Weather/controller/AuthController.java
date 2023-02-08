package com.practice.Weather.controller;

import com.practice.Weather.dto.AuthUserDto;
import com.practice.Weather.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> signUp(
            @RequestBody AuthUserDto dto
    ) {
        return ResponseEntity.ok(authService.SignUp(dto));
    }

    @PostMapping("/authentication")
    public ResponseEntity<String> signIn(
            @RequestBody AuthUserDto dto
    ) {
        return ResponseEntity.ok(authService.SignIn(dto));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello()
    {
        return ResponseEntity.ok("Hello");
    }
}
