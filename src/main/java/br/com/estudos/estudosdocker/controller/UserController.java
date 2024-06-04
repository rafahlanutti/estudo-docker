package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.dto.*;
import br.com.estudos.estudosdocker.service.AuthenticationService;
import br.com.estudos.estudosdocker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/save")
    public ResponseEntity<UserResponse> saveUser(@RequestBody final UserRequest userRequest) {
        return ResponseEntity.ok(userService.saveUser(userRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        return ResponseEntity.ok().body(userService.getUser());
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> AuthenticateAndGetToken(@RequestBody final AuthRequest authRequest) {
       return ResponseEntity.ok().body(authenticationService.authenticate(authRequest));
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody final RefreshTokenRequest refreshTokenRequest) {
        return authenticationService.refreshToken(refreshTokenRequest);
    }
}
