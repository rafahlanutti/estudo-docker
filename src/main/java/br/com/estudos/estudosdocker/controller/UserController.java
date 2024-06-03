package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.domain.RefreshToken;
import br.com.estudos.estudosdocker.dto.*;
import br.com.estudos.estudosdocker.service.JwtService;
import br.com.estudos.estudosdocker.service.RefreshTokenService;
import br.com.estudos.estudosdocker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/save")
    public ResponseEntity<UserResponse> saveUser(@RequestBody final UserRequest userRequest) {
        return ResponseEntity.ok(userService.saveUser(userRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        return ResponseEntity.ok().body(userService.getUser());
    }

    @PostMapping("/login")
    public JwtResponse AuthenticateAndGetToken(@RequestBody final AuthRequest authRequest) {
        //TODO: Refatorar essa logica para uma service
        final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            final var refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .token(refreshToken.getToken()).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody final RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token não encontrado."));
    }
}
