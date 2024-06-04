package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.dto.UserRequest;
import br.com.estudos.estudosdocker.dto.UserResponse;
import br.com.estudos.estudosdocker.mapper.UserMapper;
import br.com.estudos.estudosdocker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUser_NewUser() {
        final var userRequest = new UserRequest();
        userRequest.setId(null);
        userRequest.setPassword("rawPassword");

        final var userInfo = new UserInfo();
        userInfo.setPassword("encodedPassword");

        final var userResponse = new UserResponse();

        when(userMapper.map(any(UserRequest.class))).thenReturn(userInfo);
        when(userMapper.map(any(UserInfo.class))).thenReturn(userResponse);
        when(userRepository.save(any(UserInfo.class))).thenReturn(userInfo);

        final var savedUser = userService.saveUser(userRequest);

        assertNotNull(savedUser);
        verify(userMapper, times(1)).map(userRequest);
        verify(userMapper, times(1)).map(userInfo);
        verify(userRepository, times(1)).save(userInfo);
    }

    @Test
    void testSaveUser_UserAlreadyExists() {
        final var userRequest = new UserRequest();
        userRequest.setId(1L);
        userRequest.setPassword("rawPassword");

        final var existingUser = new UserInfo();

        when(userRepository.findFirstById(anyLong())).thenReturn(existingUser);
        when(userMapper.map(any(UserRequest.class))).thenReturn(existingUser);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.saveUser(userRequest);
        });

        assertEquals("Usuario já cadastrado.", exception.getMessage());
        verify(userRepository, times(1)).findFirstById(1L);
        verify(userMapper, times(1)).map(any(UserRequest.class));
        verify(userRepository, times(0)).save(any(UserInfo.class));
    }

    @Test
    void testGetUser() {
        final var userDetails = mock(UserDetails.class);
        final var authentication = mock(Authentication.class);
        final var securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        SecurityContextHolder.setContext(securityContext);

        final var userInfo = new UserInfo();
        final var userResponse = new UserResponse();

        when(userRepository.findByUsername(anyString())).thenReturn(userInfo);
        when(userMapper.map(any(UserInfo.class))).thenReturn(userResponse);

        final var response = userService.getUser();

        assertNotNull(response);
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(userMapper, times(1)).map(userInfo);
    }

    @Test
    void testGetAllUser() {
        final var userList = Stream.of(new UserInfo(), new UserInfo()).toList();
        final var userResponseList = Stream.of(new UserResponse(), new UserResponse()).toList();

        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.map(any(UserInfo.class))).thenReturn(userResponseList.get(0), userResponseList.get(1));

        final var responseList = userService.getAllUser();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).map(any(UserInfo.class));
    }
}
