package com.example.service.auth;

import com.example.model.AuthenticationResponse;
import com.example.model.Role;
import com.example.model.dto.UserDto;
import com.example.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.model.dto.UserDto.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(UserDto userRequestDTO) {
        User user = User.builder()
            .username(userRequestDTO.getUsername())
            .password(passwordEncoder.encode(userRequestDTO.getPassword()))
            .email(userRequestDTO.getEmail())
            .role(Role.USER)
            .build();

        UserDto saved = userService.save(user);
        return getAuthenticationResponse(toEntity(saved));
    }

    public AuthenticationResponse authenticate(UserDto user) {
        UserDto userByEmail = userService.findByEmail(user.getEmail());
        return getAuthenticationResponse(toEntity(userByEmail));
    }

    private AuthenticationResponse getAuthenticationResponse(User saved) {
        String generatedToken = jwtService.generateToken(saved);

        return AuthenticationResponse.builder()
            .token(generatedToken)
            .build();
    }
}
