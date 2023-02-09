package com.practice.Weather.service;

import com.practice.Weather.payload.AuthUserDto;

public interface AuthService {
    String SignUp(AuthUserDto dto);
    String SignIn(AuthUserDto dto);
}
