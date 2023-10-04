package com.api.superHero.service;

import com.api.superHero.dao.request.SignUpRequest;
import com.api.superHero.dao.request.SigninRequest;
import com.api.superHero.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
