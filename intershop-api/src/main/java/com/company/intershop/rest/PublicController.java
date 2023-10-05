package com.company.intershop.rest;

import com.company.intershop.service.ProductService;
import com.company.intershop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/numberOfUsers")
    public Integer getNumberOfUsers() {
        return userService.getUsers().size();
    }

    @GetMapping("/numberOfProducts")
    public Integer getNumberOfProducts() {
        return productService.getProducts().size();
    }
}
