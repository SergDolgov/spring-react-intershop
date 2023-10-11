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
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getUsers() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andDo(print());        
    }

    @Test
    public void getUserByName() throws Exception {
        mockMvc.perform(get("/api/users/{username}", "admin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("admin")))
                .andExpect(jsonPath("$.email", equalTo("admin@mycompany.com")))
                .andExpect(jsonPath("$.role", equalTo("ADMIN")));
    }

    @Test
    public void deleteUserByName() throws Exception {
        mockMvc.perform(delete("/api/users/{username}", "user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("user")));
    }

}
