package com.company.intershop.service;

import com.company.intershop.model.Product;
import com.company.intershop.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    void deleteUser(User user);

}