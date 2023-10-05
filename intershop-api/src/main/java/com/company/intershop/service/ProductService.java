package com.company.intershop.service;

import com.company.intershop.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findByIdIn(List<Long> Ids);
    
    List<Product> getProducts();

    List<Product> getProductsContainingText(String text);

    Product validateAndGetProduct(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Product product);
}
