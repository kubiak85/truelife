package com.truelife.restapi.service;

import com.truelife.restapi.payload.dto.model.JwtDto;
import com.truelife.restapi.payload.dto.model.SignInDto;
import com.truelife.restapi.payload.dto.model.SignUpDto;
import com.truelife.restapi.payload.dto.model.UserDto;

public interface AuthService {
    void signUp(SignUpDto signUpDto);
    JwtDto signIn(SignInDto signInDto);
}
