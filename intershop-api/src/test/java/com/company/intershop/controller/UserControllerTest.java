package com.company.intershop.controller;

import com.company.intershop.domain.User;
import com.company.intershop.repository.UserRepository;
import com.company.intershop.rest.AuthController;
import com.company.intershop.rest.UserController;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.company.intershop.constants.ErrorMessage.EMPTY_FIRST_NAME;
import static com.company.intershop.constants.ErrorMessage.EMPTY_LAST_NAME;
import static com.company.intershop.constants.PathConstants.*;
import static com.company.intershop.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(UserController.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {
//    @MockBean
//    private UserRepository userRepository;
//    @Autowired
//    private UserServiceImpl userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithUserDetails("admin")
    public void getUsers() throws Exception {
        
        List<User> Users = new ArrayList<>(
                Arrays.asList(
                    new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
                    new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
                ));
//        when(userRepository.findByUsername(username)
//        when(userRepository.findAll()).thenReturn(Users);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(Users.size()))
                .andDo(print());        
    }

    @Test
    public void getUserInfoByJwt() throws Exception {
        LoginRequest userRequest = new LoginRequest();
        userRequest.setUsername(USER_NAME);
        userRequest.setPassword("u");

        User user = new User();
        user.setUsername(USER_NAME);
        //user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPassword("$2a$10$bFwKPxtUMmYOvtDeB2RRYu6lxVKVjE/xAlbWWiHuek1iLAimKAh3C");
        user.setRole(ROLE_USER);
        user.setEmail(USER_EMAIL);

        String JWT_TOKEN1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2OTcwMTAxMjQsImlhdCI6MTY5NzAwOTUyNCwianRpIjoiNDU5NWRlMTUtZTdiYS00OTIwLWJjZTEtYmNhNTllMTRhNTA2IiwiaXNzIjoib3JkZXItYXBpIiwiYXVkIjoib3JkZXItYXBwIiwic3ViIjoidXNlciIsInJvbCI6WyJVU0VSIl0sInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.ADSsZDX-1BZKpcZvlwRtTsyf-RyAGMDodUhwHvySpcadgNukAHjDu1-B_e8SBiGO3UkO4A5twgj34zl8jeCIIA";

       // when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

//        mockMvc.perform(post("/auth/authenticate")
//                        .content(mapper.writeValueAsString(userRequest))
                mockMvc.perform(get("/api/users/me")
                        .header("Authorization", JWT_TOKEN1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(USER_EMAIL))
                .andExpect(jsonPath("$.role").value(ROLE_USER));
    }
    //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2OTcwMTAxMjQsImlhdCI6MTY5NzAwOTUyNCwianRpIjoiNDU5NWRlMTUtZTdiYS00OTIwLWJjZTEtYmNhNTllMTRhNTA2IiwiaXNzIjoib3JkZXItYXBpIiwiYXVkIjoib3JkZXItYXBwIiwic3ViIjoidXNlciIsInJvbCI6WyJVU0VSIl0sInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.ADSsZDX-1BZKpcZvlwRtTsyf-RyAGMDodUhwHvySpcadgNukAHjDu1-B_e8SBiGO3UkO4A5twgj34zl8jeCIIA
    public void login() throws Exception {
//        List<User> Users = new ArrayList<>(
//                Arrays.asList(
//                        new User("admin", "$2a$10$GChOhed69wgE.mrntbR.rO3wtWQvVhK0/7ObCjTYobHVAGtfHu0Gy", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
//                        new User("user", "$2a$10$bFwKPxtUMmYOvtDeB2RRYu6lxVKVjE/xAlbWWiHuek1iLAimKAh3C", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
//                ));

        LoginRequest userRequest = new LoginRequest();
        userRequest.setUsername(USER_NAME);
        userRequest.setPassword("u");

        User user = new User();
        user.setUsername(USER_NAME);
        //user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPassword("$2a$10$bFwKPxtUMmYOvtDeB2RRYu6lxVKVjE/xAlbWWiHuek1iLAimKAh3C");
        user.setRole("USER");

      //  when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(user));
        //userService.getUserByUsername(USER_NAME);

//        when(userRepository.findAll()).thenReturn(Users);

        mockMvc.perform(post("/auth/authenticate")
                        .content(mapper.writeValueAsString(userRequest))
                        //mockMvc.perform(get(API_V1_USERS)
                        //        .header("Authorization", JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").isNotEmpty())
//                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
//                .andExpect(jsonPath("$.role").value(ROLE_ADMIN));
    }

    @Test
    public void getNumberOfUsers() throws Exception {
        List<User> Users = new ArrayList<>(
                Arrays.asList(
                        new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
                        new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
                ));

        //when(userRepository.findAll()).thenReturn(Users);

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
