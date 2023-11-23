package com.application.security.service;

import com.application.security.dto.JwtAuthenticationResponse;
import com.application.security.dto.RefreshTokenRequest;
import com.application.security.dto.SignInRequest;
import com.application.security.dto.SignUpRequest;
import com.application.security.entity.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);

}
