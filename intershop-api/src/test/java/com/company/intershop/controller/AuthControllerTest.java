package com.company.intershop.controller;

import com.company.intershop.domain.User;
import com.company.intershop.repository.UserRepository;
import com.company.intershop.rest.dto.LoginRequest;
import com.company.intershop.rest.dto.SignUpRequest;
import com.company.intershop.security.WebSecurityConfig;
import com.company.intershop.security.oauth2.OAuth2Provider;
import com.company.intershop.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.company.intershop.constants.ErrorMessage.EMPTY_FIRST_NAME;
import static com.company.intershop.constants.ErrorMessage.EMPTY_LAST_NAME;
import static com.company.intershop.constants.PathConstants.API_V1_USERS;
import static com.company.intershop.util.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(UserController.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class AuthControllerTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void login() throws Exception {
        LoginRequest userRequest = new LoginRequest();
        userRequest.setUsername(USER_NAME);
        userRequest.setPassword("u");

        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword("$2a$10$bFwKPxtUMmYOvtDeB2RRYu6lxVKVjE/xAlbWWiHuek1iLAimKAh3C");
        user.setRole("USER");

        when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

        mockMvc.perform(post("/auth/authenticate")
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
    //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2OTcwMTAxMjQsImlhdCI6MTY5NzAwOTUyNCwianRpIjoiNDU5NWRlMTUtZTdiYS00OTIwLWJjZTEtYmNhNTllMTRhNTA2IiwiaXNzIjoib3JkZXItYXBpIiwiYXVkIjoib3JkZXItYXBwIiwic3ViIjoidXNlciIsInJvbCI6WyJVU0VSIl0sInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.ADSsZDX-1BZKpcZvlwRtTsyf-RyAGMDodUhwHvySpcadgNukAHjDu1-B_e8SBiGO3UkO4A5twgj34zl8jeCIIA

    @Test
    public void getNumberOfUsers() throws Exception {
        List<User> Users = new ArrayList<>(
                Arrays.asList(
                        new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
                        new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
                ));

        when(userRepository.findAll()).thenReturn(Users);

        mockMvc.perform(get("/public/numberOfUsers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Users.size())
                );
    }



    @Test()
    public void getUserInfoByJwtExpired() throws Exception {
        mockMvc.perform(get(API_V1_USERS)
                        .header("Authorization", "jwt")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserInfo() throws Exception {
        SignUpRequest userRequest = new SignUpRequest();
        userRequest.setName(USER2_NAME);
        userRequest.setUsername(USER2_NAME);
        userRequest.setEmail(USER2_EMAIL);

        mockMvc.perform(put(API_V1_USERS)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.name").value(USER2_NAME))
                .andExpect(jsonPath("$.username").value(USER2_NAME));
    }

    @Test
    @WithUserDetails(USER_EMAIL)
    public void updateUserInfo_ShouldInputFieldsAreEmpty() throws Exception {
        SignUpRequest userRequest = new SignUpRequest();

        mockMvc.perform(put(API_V1_USERS)
                        .content(mapper.writeValueAsString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nameError", is(EMPTY_FIRST_NAME)))
                .andExpect(jsonPath("$.userNameError", is(EMPTY_LAST_NAME)));
    }



}
