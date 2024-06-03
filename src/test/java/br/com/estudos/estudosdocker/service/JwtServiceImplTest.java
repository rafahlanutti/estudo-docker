package br.com.estudos.estudosdocker.service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();
    }

    @Test
    void testGenerateToken() {
        // Given
        String username = "testuser";

        // When
        String token = jwtService.generateToken(username);

        // Then
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        // Given
        String username = "testuser";
        String token = jwtService.generateToken(username);

        // When
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateToken() {
        // Given
        String username = "testuser";
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        String token = jwtService.generateToken(username);

        // When
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testExtractClaim() {
        // Given
        String username = "testuser";
        String token = jwtService.generateToken(username);

        // When
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);

        // Then
        assertNotNull(expiration);
    }
}