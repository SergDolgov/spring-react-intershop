package com.company.intershop.service;

import com.company.intershop.domain.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> getBrands();

    Brand getBrandByName(String name);

    Brand saveBrand(Brand brand);

    void deleteBrand(Brand brand);

    Brand findById(Long brandId);

}
