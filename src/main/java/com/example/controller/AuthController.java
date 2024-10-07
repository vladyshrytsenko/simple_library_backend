package com.example.controller;

import com.example.model.AuthenticationResponse;
import com.example.model.dto.UserDto;
import com.example.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDto userRequestDto) {

        AuthenticationResponse registered = authenticationService.register(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserDto userRequestDto) {

        AuthenticationResponse authenticated = authenticationService.authenticate(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(authenticated);
    }

}
