package com.company.intershop.mapper;

import com.company.intershop.domain.Product;
import com.company.intershop.rest.dto.ProductRequest;
import com.company.intershop.rest.dto.ProductDto;

public interface ProductMapper {

    Product toProduct(ProductRequest productRequest);

    ProductDto toProductDto(Product product);
}