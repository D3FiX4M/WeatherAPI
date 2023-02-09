package com.practice.Weather.service.Impl;

import com.practice.Weather.domain.repository.UserRepository;
import com.practice.Weather.domain.spec.UserSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findOne(UserSpec.usernameEq(username))
                .orElseThrow(
                        ()-> new UsernameNotFoundException("User not found")
                );
    }

}
