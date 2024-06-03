package br.com.estudos.estudosdocker.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    <T> T extractClaim(final String token, Function<Claims, T> claimsResolver);
    Boolean validateToken(final String token, final UserDetails userDetails);
    String generateToken(final String username);
    String extractUsername(final String token);
}
