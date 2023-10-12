package com.company.intershop.runner;

import com.company.intershop.domain.Brand;
import com.company.intershop.domain.Product;
import com.company.intershop.domain.User;
import com.company.intershop.security.oauth2.OAuth2Provider;
import com.company.intershop.security.WebSecurityConfig;
import com.company.intershop.service.BrandService;
import com.company.intershop.service.ProductService;
import com.company.intershop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserService userService;
    private final BrandService brandService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;

    private Brand LG, Nokia, Samsung;

    @Override
    public void run(String... args) {

        if (true) return;

        if (!brandService.getBrands().isEmpty()) {
            return;
        }

        if (!userService.getUsers().isEmpty()) {
            return;
        }

        BRANDS.forEach(brandService::saveBrand);

        USERS.forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
        });

        LG = brandService.getBrandByName("LG");
        Nokia = brandService.getBrandByName("Nokia");
        Samsung = brandService.getBrandByName("Samsung");

        PRODUCTS.forEach(productService::saveProduct);

        log.info("Database initialized");
    }

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "a", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN, null, OAuth2Provider.LOCAL, "1"),
            new User("user", "u", "User", "user@mycompany.com", WebSecurityConfig.USER, null, OAuth2Provider.LOCAL, "2")
    );

    private static final List<Brand> BRANDS = Arrays.asList(
            new Brand("LG"),
            new Brand("Nokia"),
            new Brand("Samsung")
    );

    private final List<Product> PRODUCTS = Arrays.asList(
            new Product(LG, "I, Tonya", "https://m.media-amazon.com/images/M/MV5BMjI5MDY1NjYzMl5BMl5BanBnXkFtZTgwNjIzNDAxNDM@._V1_SX300.jpg", 999.99f),
            new Product(LG,"American Pie", "https://m.media-amazon.com/images/M/MV5BMTg3ODY5ODI1NF5BMl5BanBnXkFtZTgwMTkxNTYxMTE@._V1_SX300.jpg", 1999.99f),
            new Product(LG,"I Am Legend", "https://m.media-amazon.com/images/M/MV5BYTE1ZTBlYzgtNmMyNS00ZTQ2LWE4NjEtZjUxNDJkNTg2MzlhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg", 2999.99f),
            new Product(Nokia,"Resident Evil", "https://m.media-amazon.com/images/M/MV5BZmI1ZGRhNDYtOGVjZC00MmUyLThlNTktMTQyZGE3MzE1ZTdlXkEyXkFqcGdeQXVyNDE5MTU2MDE@._V1_SX300.jpg", 899.99f),
            new Product(Nokia,"Rocky", "https://m.media-amazon.com/images/M/MV5BMTY5MDMzODUyOF5BMl5BanBnXkFtZTcwMTQ3NTMyNA@@._V1_SX300.jpg", 799.99f),
            new Product(Nokia,"The Green Line", "https://m.media-amazon.com/images/M/MV5BMzZkNTRjZjEtNDVhNi00NGEyLWE2NWYtNTUzOTFlNGVmMDFjXkEyXkFqcGdeQXVyODg0NjM4MDg@._V1_SX300.jpg", 599.99f),
            new Product(Nokia,"Joker", "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg", 699.99f),
            new Product(Samsung,"Braveheart", "https://m.media-amazon.com/images/M/MV5BMzkzMmU0YTYtOWM3My00YzBmLWI0YzctOGYyNTkwMWE5MTJkXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", 999.99f),
            new Product(Samsung,"Into the Wild", "https://m.media-amazon.com/images/M/MV5BMTAwNDEyODU1MjheQTJeQWpwZ15BbWU2MDc3NDQwNw@@._V1_SX300.jpg", 399.99f),
            new Product(Samsung,"Back to the Future", "https://m.media-amazon.com/images/M/MV5BZmU0M2Y1OGUtZjIxNi00ZjBkLTg1MjgtOWIyNThiZWIwYjRiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg", 999.99f)
    );
}
