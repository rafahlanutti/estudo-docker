package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.dto.JwtResponse;
import br.com.estudos.estudosdocker.dto.UserRequest;
import br.com.estudos.estudosdocker.dto.UserResponse;
import br.com.estudos.estudosdocker.filter.JwtAuthFilter;
import br.com.estudos.estudosdocker.service.AuthenticationService;
import br.com.estudos.estudosdocker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
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
    private AuthenticationService authenticationService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
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

        mockMvc.perform(get("/api/v1/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(userResponse.getUsername()));
    }

    @Test
    void testAuthenticateAndGetToken() throws Exception {
        final var token = JwtResponse.builder()
                .accessToken(ACCESS_TOKEN)
                .token(REFRESH_TOKEN).build();

        when(authenticationService.authenticate(any())).thenReturn(token);
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
        final var token = JwtResponse.builder()
                .accessToken(ACCESS_TOKEN)
                .token(REFRESH_TOKEN).build();
        when(authenticationService.refreshToken(any())).thenReturn(token);

        mockMvc.perform(post("/api/v1/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"token\": \"refresh_token\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.token").value(REFRESH_TOKEN));
    }
}