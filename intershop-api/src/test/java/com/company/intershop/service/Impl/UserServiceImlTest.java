package com.company.intershop.service.Impl;

import com.company.intershop.domain.User;
import com.company.intershop.repository.ProductRepository;
import com.company.intershop.repository.UserRepository;
import com.company.intershop.security.WebSecurityConfig;
import com.company.intershop.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.intershop.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImlTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;


    @Test
    public void getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        userService.getUsers();

        when(userRepository.findAll()).thenReturn(users);
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }


    @Test
    public void getUserByUsername() {
        User user = new User();
        user.setUsername(USER_NAME);

        when(userRepository.findByUsername(USER_NAME)).thenReturn(Optional.of(user));
        userService.getUserByUsername(USER_NAME);
        assertEquals(USER_NAME, user.getUsername());
        verify(userRepository, times(1)).findByUsername(USER_NAME);
    }

    @Test
    public void getUserByEmail() {
        User user = new User();
        user.setEmail(USER_EMAIL);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        userService.getUserByEmail(USER_EMAIL);
        assertEquals(USER_EMAIL, user.getEmail());
        verify(userRepository, times(1)).findByEmail(USER_EMAIL);
    }


    @Test
    public void loadUserByUsername() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setName(FIRST_NAME);
        user.setRole(WebSecurityConfig.USER);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(FIRST_NAME, user.getName());
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setName(FIRST_NAME);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        userService.saveUser(user);
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(FIRST_NAME, user.getName());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }
}
