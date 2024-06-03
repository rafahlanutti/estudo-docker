package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.RefreshToken;
import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testSaved() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));
        final var userInfo = new UserInfo(1, "username", "email@example.com", roles);
        final var refreshToken = new RefreshToken("1", "token123", Instant.now().plusSeconds(300), userInfo);

        // When
        final var saved = repository.save(refreshToken);

        // Then
        assertNotNull(saved);
        assertEquals(refreshToken.getId(), saved.getId());
        assertEquals(refreshToken.getToken(), saved.getToken());
        assertEquals(refreshToken.getExpiryDate(), saved.getExpiryDate());
        assertEquals(refreshToken.getUserInfo(), saved.getUserInfo());
    }

    @Test
    void testFindAll() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));
        final var userInfo = new UserInfo(1, "username", "email@example.com", roles);
        final var refreshToken = new RefreshToken("1", "token123", Instant.now().plusSeconds(300), userInfo);

        repository.save(refreshToken);

        // When
        final var refreshTokens = repository.findAll();

        // Then
        assertEquals(1, refreshTokens.size());
        assertEquals("token123", refreshTokens.get(0).getToken());
    }

    @Test
    void testFindByToken() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));
        final var userInfo = new UserInfo(1, "username", "email@example.com", roles);
        final var refreshToken = new RefreshToken("1", "token123", Instant.now().plusSeconds(300), userInfo);

        repository.save(refreshToken);

        // When
        final var foundToken = repository.findByToken("token123");

        // Then
        assertTrue(foundToken.isPresent());
        assertEquals("token123", foundToken.get().getToken());
        assertEquals(userInfo, foundToken.get().getUserInfo());
    }
}