package com.truelife.restapi.payload.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class JwtDto {
    private String tokenType = "Bearer";
    private String accessToken;
    private String username;
    private String email;
    private List<String> roles;

    public JwtDto(String accessToken, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
