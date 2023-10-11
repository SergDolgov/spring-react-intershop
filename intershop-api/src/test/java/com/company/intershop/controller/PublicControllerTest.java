package com.company.intershop.controller;

import com.company.intershop.domain.Product;
import com.company.intershop.domain.User;
import com.company.intershop.repository.ProductRepository;
import com.company.intershop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PublicControllerTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    public void getNumberOfUsers() throws Exception {
        List<User> Users = new ArrayList<>(
                Arrays.asList(new User(), new User(), new User()));

        when(userRepository.findAll()).thenReturn(Users);

        mockMvc.perform(get("/public/numberOfUsers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Users.size())
                );
    }

    @Test
    public void getNumberOfProducts() throws Exception {
        List<Product> products = new ArrayList<>(
                Arrays.asList(new Product(), new Product()));

        when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(get("/public/numberOfProducts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(products.size())
                );
    }

}
