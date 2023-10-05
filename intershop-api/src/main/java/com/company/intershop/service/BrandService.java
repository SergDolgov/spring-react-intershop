package com.company.intershop.service;

import com.company.intershop.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<Brand> getBrands();

    Brand getBrandByName(String name);

    Brand saveBrand(Brand brand);

    void deleteBrand(Brand brand);

    Brand findById(Long brandId);

}
