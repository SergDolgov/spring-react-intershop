package com.company.intershop.service;

import com.company.intershop.exception.ProductNotFoundException;
import com.company.intershop.model.Product;
import com.company.intershop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByIdIn(List<Long> Ids) {
        return productRepository.findByIdIn(Ids);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAllByOrderByTitle();
    }

    @Override
    public List<Product> getProductsContainingText(String text) {
        return productRepository.findByTitleContainingIgnoreCaseOrderByTitle(text);
    }

    @Override
    public Product validateAndGetProduct(Long id) {
        return productRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id %s not found", id)));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}
