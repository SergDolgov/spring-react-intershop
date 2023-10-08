package com.company.intershop.controller;

import com.company.intershop.domain.User;
import com.company.intershop.repository.UserRepository;
import com.company.intershop.rest.UserController;
import com.company.intershop.rest.dto.SignUpRequest;
import com.company.intershop.security.WebSecurityConfig;
import com.company.intershop.security.oauth2.OAuth2Provider;
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
//@Sql(value = {"/sql/create-user-before.sql"},
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/sql/create-user-after.sql"},
//        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getUsers() throws Exception {
        
        List<User> Users = new ArrayList<>(
                Arrays.asList(
                    new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
                    new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
                ));

        when(userRepository.findAll()).thenReturn(Users);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(Users.size()))
                .andDo(print());        
    }

    @Test
    public void getUserInfoByJwt() throws Exception {
        mockMvc.perform(get(API_V1_USERS)
                        .header("Authorization", JWT_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.email").value(ADMIN_EMAIL))
                .andExpect(jsonPath("$.role").value(ROLE_ADMIN));
    }

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
