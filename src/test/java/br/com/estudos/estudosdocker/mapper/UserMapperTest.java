package br.com.estudos.estudosdocker.mapper;

import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.domain.UserRole;
import br.com.estudos.estudosdocker.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToUserInfo() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));
        final var request = new UserRequest(1L, "testuser", "password", roles);

        // When
        final var user = mapper.map(request);

        // Then
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(roles, user.getRoles());
    }

    @Test
    void testToUserResponse() {
        // Given
        final var roles = Set.of(new UserRole(1L, "ROLE_USER"));
        final var user = new UserInfo(1L, "testuser", "password", roles);

        // When
        final var request = mapper.map(user);

        // Then
        assertEquals(1L, request.getId());
        assertEquals("testuser", request.getUsername());
        assertEquals(roles, request.getRoles());
    }

}