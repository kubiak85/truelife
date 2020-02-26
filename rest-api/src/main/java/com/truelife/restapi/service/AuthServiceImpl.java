package com.truelife.restapi.service;

import com.truelife.restapi.exception.AppException;
import com.truelife.restapi.exception.AuthException;
import com.truelife.restapi.model.Role;
import com.truelife.restapi.model.RoleName;
import com.truelife.restapi.model.User;
import com.truelife.restapi.payload.dto.model.JwtDto;
import com.truelife.restapi.payload.dto.model.SignInDto;
import com.truelife.restapi.payload.dto.model.SignUpDto;
import com.truelife.restapi.repository.RoleRepository;
import com.truelife.restapi.repository.UserRepository;
import com.truelife.restapi.security.jwt.JwtTokenProvider;
import com.truelife.restapi.security.jwt.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl  implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    public void signUp(SignUpDto signUpDto) {
        if(userRepository.existsByUsername(signUpDto.getUsername()))
            throw new AuthException.DuplicateEntityException("Nazwa użytkownika jest już w użyciu!");

        if(userRepository.existsByEmail(signUpDto.getEmail()))
            throw new AuthException.DuplicateEntityException("Podany adres email jest już w użyciu!");

        // Creating user's account
        User user = new User(
                signUpDto.getName(),
                signUpDto.getUsername(),
                signUpDto.getEmail(),
                signUpDto.getPassword()
        );

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleName role;
        if(signUpDto.getRole().compareTo(RoleName.ROLE_ARTIST.toString()) == 0)
            role = RoleName.ROLE_ARTIST;
        else if(signUpDto.getRole().compareTo(RoleName.ROLE_FAN.toString()) == 0)
            role = RoleName.ROLE_FAN;
        else
            throw new AppException.BadRequestException("Wystąpił błąd w roli.");

        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new AppException.RunningException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        user.setActivateUser(false);
        user.setBanUser(false);
        userRepository.save(user);
    }

    @Override
    public JwtDto signIn(SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getLogin(),
                        signInDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtDto(jwt,
                userDetails.getUsername(),
                userDetails.getName(),
                roles);
    }
}
