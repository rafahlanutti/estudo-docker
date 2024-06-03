package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.domain.RefreshToken;
import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.dto.UserRequest;
import br.com.estudos.estudosdocker.dto.UserResponse;
import br.com.estudos.estudosdocker.filter.JwtAuthFilter;
import br.com.estudos.estudosdocker.service.JwtService;
import br.com.estudos.estudosdocker.service.RefreshTokenService;
import br.com.estudos.estudosdocker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String USERNAME_TEST_PASSWORD_PASSWORD = "{ \"username\": \"test\", \"password\": \"password\" }";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @Mock
    private Authentication authentication;
    private UserResponse userResponse;
    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
        refreshToken = new RefreshToken();
        refreshToken.setToken(REFRESH_TOKEN);

        final var userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUsername("arroz");
        userInfo.setPassword("123");

        refreshToken.setUserInfo(userInfo);
    }

    @Test
    void testSaveUser() throws Exception {
        when(userService.saveUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/v1/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USERNAME_TEST_PASSWORD_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUser()).thenReturn(Collections.singletonList(userResponse));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value(userResponse.getUsername()));
    }

    @Test
    void testGetUserProfile() throws Exception {
        when(userService.getUser()).thenReturn(userResponse);

        mockMvc.perform(post("/api/v1/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    void testAuthenticateAndGetToken() throws Exception {
        when(jwtService.generateToken(anyString())).thenReturn(ACCESS_TOKEN);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(refreshTokenService.createRefreshToken(any())).thenReturn(refreshToken);

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USERNAME_TEST_PASSWORD_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.token").value(REFRESH_TOKEN));
    }

    @Test
    void testRefreshToken() throws Exception {
        when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(any(RefreshToken.class))).thenReturn(refreshToken);
        when(jwtService.generateToken(anyString())).thenReturn(ACCESS_TOKEN);

        mockMvc.perform(post("/api/v1/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"token\": \"refresh_token\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.token").value(REFRESH_TOKEN));
    }
}