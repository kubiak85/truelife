package com.truelife.restapi.controller;

import com.truelife.restapi.payload.dto.model.SignInDto;
import com.truelife.restapi.payload.dto.model.SignUpDto;
import com.truelife.restapi.payload.request.SignInRequest;
import com.truelife.restapi.payload.request.SignUpRequest;
import com.truelife.restapi.payload.response.Response;
import com.truelife.restapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public Response signIn(@Valid @RequestBody SignInRequest signInRequest){
        SignInDto signInDto = new SignInDto(
                signInRequest.getUsernameOrEmail(),
                signInRequest.getPassword());
        return Response.ok().setPayload(authService.signIn(signInDto));
    }

    @PostMapping("/signup")
    public Response signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        SignUpDto signUpDto = new SignUpDto(signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getRole());

        authService.signUp(signUpDto);
        return Response.ok().setPayload("Twoje konto zostało pomyślnie utworzone.");
    }
}