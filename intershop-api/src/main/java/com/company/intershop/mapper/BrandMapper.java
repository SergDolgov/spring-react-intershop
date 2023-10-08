package com.company.intershop.mapper;

import com.company.intershop.domain.Brand;
import com.company.intershop.rest.dto.BrandDto;

public interface BrandMapper {

    BrandDto toBrandDto(Brand brand);
}