package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.RefreshToken;
import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.repository.RefreshTokenRepository;
import br.com.estudos.estudosdocker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @Test
    void testCreateRefreshToken() {
        final var userInfo = createUserInfo();
        final var mockToken = createRefreshToken();

        when(userRepository.findByUsername(anyString())).thenReturn(userInfo);
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(mockToken);

        final var username = "testUser";
        final var createdToken = refreshTokenService.createRefreshToken(username);

        assertNotNull(createdToken);
        assertEquals(mockToken.getToken(), createdToken.getToken());
        verify(userRepository, times(1)).findByUsername(username);
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void testFindByToken() {
        final var mockToken = createRefreshToken();
        final var token = "testToken";

        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(mockToken));

        final var foundToken = refreshTokenService.findByToken(token);

        assertTrue(foundToken.isPresent());
        assertEquals(mockToken.getToken(), foundToken.get().getToken());
        verify(refreshTokenRepository, times(1)).findByToken(token);
    }

    @Test
    void testVerifyExpiration_NotExpired() {
        final var userInfo = createUserInfo();
        final var mockToken = createRefreshToken(userInfo);

        final var verifiedToken = refreshTokenService.verifyExpiration(mockToken);

        assertNotNull(verifiedToken);
        assertEquals(mockToken.getToken(), verifiedToken.getToken());
    }

    @Test
    void testVerifyExpiration_Expired() {
        final var userInfo = createUserInfo();
        final var mockToken = RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .userInfo(userInfo)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().minusMillis(1000))
                .build();


        doNothing().when(refreshTokenRepository).delete(mockToken);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            refreshTokenService.verifyExpiration(mockToken);
        });

        assertEquals(mockToken.getToken() + " O token expirou. Por favor, faça um novo login.", exception.getMessage());
        verify(refreshTokenRepository, times(1)).delete(mockToken);
    }

    private RefreshToken createRefreshToken(final UserInfo... userInfo) {
        return RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .userInfo(userInfo.length > 0 ? userInfo[0] : null)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
    }

    private UserInfo createUserInfo() {
        final var userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUsername("username");
        userInfo.setPassword("123");
        return userInfo;
    }
}
