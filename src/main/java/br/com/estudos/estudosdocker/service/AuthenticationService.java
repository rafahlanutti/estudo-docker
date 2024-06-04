package br.com.estudos.estudosdocker.service;


import br.com.estudos.estudosdocker.dto.AuthRequest;
import br.com.estudos.estudosdocker.dto.JwtResponse;
import br.com.estudos.estudosdocker.dto.RefreshTokenRequest;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {
    JwtResponse authenticate(final AuthRequest authRequest) throws AuthenticationException;

    JwtResponse refreshToken(final RefreshTokenRequest refreshTokenRequest);
}
