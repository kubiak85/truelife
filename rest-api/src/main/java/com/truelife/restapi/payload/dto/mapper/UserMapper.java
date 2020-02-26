package com.truelife.restapi.payload.dto.mapper;

import com.truelife.restapi.model.User;
import com.truelife.restapi.payload.dto.model.RoleDto;
import com.truelife.restapi.payload.dto.model.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setName(user.getName())
                .setRoles(user.getRoles())
                .setUsername(user.getUsername());

    }
}
