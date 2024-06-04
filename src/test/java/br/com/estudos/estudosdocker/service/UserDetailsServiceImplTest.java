package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.helpers.CustomUserDetails;
import br.com.estudos.estudosdocker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        String username = "testUser";

        final var userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUsername(username);
        userInfo.setPassword("123");

        when(userRepository.findByUsername(anyString())).thenReturn(userInfo);

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        assertEquals("Usuario não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}
