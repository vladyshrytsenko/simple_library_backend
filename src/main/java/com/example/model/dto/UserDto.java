package com.example.model.dto;

import com.example.model.Role;
import com.example.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class UserDto {

    private String username;
    private String password;
    private String email;
    private Role role;

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }

    public static List<UserDto> toDtoList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }

        return users.stream()
            .map(UserDto::toDto)
            .collect(Collectors.toList());
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .email(userDto.getEmail())
            .role(userDto.getRole())
            .build();
    }
}
