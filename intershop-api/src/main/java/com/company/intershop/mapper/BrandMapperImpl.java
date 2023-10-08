package com.company.intershop.mapper;

import com.company.intershop.domain.Brand;
import com.company.intershop.rest.dto.BrandDto;
import org.springframework.stereotype.Service;

@Service
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandDto toBrandDto(Brand brand) {
        if (brand == null) {
            return null;
        }
        return new BrandDto(brand.getId(), brand.getName());
    }
}
