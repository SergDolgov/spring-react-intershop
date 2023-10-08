package com.company.intershop.mapper;

import com.company.intershop.domain.Product;
import com.company.intershop.rest.dto.CreateProductRequest;
import com.company.intershop.rest.dto.ProductDto;

public interface ProductMapper {

    Product toProduct(CreateProductRequest createProductRequest);

    ProductDto toProductDto(Product product);
}