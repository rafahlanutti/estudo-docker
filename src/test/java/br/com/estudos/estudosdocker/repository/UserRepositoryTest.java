package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testSaved() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));
        final var userInfo = new UserInfo(1L, "testuser", "email@example.com", roles);

        // When
        final var saved = userRepository.save(userInfo);

        // Then
        assertNotNull(saved);
        assertEquals(userInfo.getId(), saved.getId());
        assertEquals(userInfo.getUsername(), saved.getUsername());
    }

    @Test
    void testFindAll() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));

        final var userInfo = new UserInfo(1L, "testuser", "email@example.com", roles);

        userRepository.save(userInfo);

        // When
        final var users = userRepository.findAll();

        // Then
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void testFindByUsername() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));

        final var userInfo = new UserInfo(1L, "testuser", "email@example.com", roles);

        userRepository.save(userInfo);

        // When
        final var foundUser = userRepository.findByUsername("testuser");

        // Then
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testFindFirstById() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));

        final var userInfo = new UserInfo(1L, "testuser", "email@example.com", roles);

        userRepository.save(userInfo);

        // When
        final var foundUser = userRepository.findFirstById(1L);

        // Then
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("testuser", foundUser.getUsername());
    }
}