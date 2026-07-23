package com.dhaneesh.seatsure.auth.service;

import com.dhaneesh.seatsure.auth.dto.LoginRequest;
import com.dhaneesh.seatsure.auth.dto.LoginResponse;
import com.dhaneesh.seatsure.security.jwt.JwtService;
import com.dhaneesh.seatsure.user.dto.CreateUserRequest;
import com.dhaneesh.seatsure.user.dto.UserResponse;
import com.dhaneesh.seatsure.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserService userService,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse register(CreateUserRequest request) {
        return userService.createUser(request);
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}