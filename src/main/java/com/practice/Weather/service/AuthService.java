package com.practice.Weather.service;

import com.practice.Weather.dto.AuthUserDto;

public interface AuthService {
    String SignUp(AuthUserDto dto);
    String SignIn(AuthUserDto dto);
}
