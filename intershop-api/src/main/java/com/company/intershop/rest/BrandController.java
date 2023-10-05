package com.company.intershop.rest;

import com.company.intershop.mapper.BrandMapper;
import com.company.intershop.model.Brand;
import com.company.intershop.rest.dto.BrandDto;
import com.company.intershop.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.company.intershop.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;
    
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    public List<BrandDto> getBrands() {
        return brandService.getBrands().stream()
                .map(brandMapper::toBrandDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{id}")
    public BrandDto getBrandById(@PathVariable Long brandId) {
        return brandMapper.toBrandDto(brandService.findById(brandId));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/{name}")
    public BrandDto getBrandByName(@PathVariable String name) {
        return brandMapper.toBrandDto(brandService.getBrandByName(name));
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{name}")
    public BrandDto deleteBrand(@PathVariable String name) {
        Brand brand = brandService.getBrandByName(name);
        brandService.deleteBrand(brand);
        return brandMapper.toBrandDto(brand);
    }
}
