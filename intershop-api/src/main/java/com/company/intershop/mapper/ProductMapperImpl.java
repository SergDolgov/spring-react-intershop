package com.company.intershop.mapper;

import com.company.intershop.domain.Product;
import com.company.intershop.rest.dto.ProductRequest;
import com.company.intershop.rest.dto.ProductDto;
import com.company.intershop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final BrandService brandService;

    @Override
    public Product toProduct(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }
        return new Product(
                brandService.findById(productRequest.getBrandId()),
                productRequest.getTitle(),
                productRequest.getPoster(),
                productRequest.getPrice()
                );
    }

    @Override
    public ProductDto toProductDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(
                product.getId(),
                product.getBrand().getName(),
                product.getTitle(),
                product.getPoster(),
                product.getPrice(),
                DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(product.getCreatedAt()));
    }
}
