package com.company.intershop.rest;

import com.company.intershop.mapper.ProductMapper;
import com.company.intershop.model.Product;
import com.company.intershop.rest.dto.CreateProductRequest;
import com.company.intershop.rest.dto.ProductDto;
import com.company.intershop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.company.intershop.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PostMapping("/cart")
    public List<ProductDto> getProductsCart(@RequestBody List<Long> productsIds) {
        return productService.findByIdIn(productsIds).stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(value = "text", required = false) String text) {
        List<Product> products = (text == null) ? productService.getProducts() : productService.getProductsContainingText(text);
        return products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id)  {
        Product product = productService.validateAndGetProduct(id);
        return productMapper.toProductDto(product);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = productMapper.toProduct(createProductRequest);
        return productMapper.toProductDto(productService.saveProduct(product));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        Product product = productService.validateAndGetProduct(id);
        productService.deleteProduct(product);
        return productMapper.toProductDto(product);
    }
}
