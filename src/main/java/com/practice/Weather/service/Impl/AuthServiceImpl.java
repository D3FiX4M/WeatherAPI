package com.practice.Weather.service.Impl;

import com.practice.Weather.dto.AuthUserDto;
import com.practice.Weather.entity.Role;
import com.practice.Weather.entity.User;
import com.practice.Weather.exception.ExistException;
import com.practice.Weather.exception.NotFoundException;
import com.practice.Weather.repository.RoleRepository;
import com.practice.Weather.repository.UserRepository;
import com.practice.Weather.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public String SignUp(AuthUserDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())){
            throw new ExistException("User with username already exist");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(
                        ()-> new NotFoundException("Role not found")
                );

        User newUser = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .build();

        userRepository.save(newUser);

        return "User registration successfully";
    }

    @Override
    public String SignIn(AuthUserDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User authorisation successfully";

    }
}
