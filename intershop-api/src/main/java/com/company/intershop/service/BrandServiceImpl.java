package com.company.intershop.service;

import com.company.intershop.domain.Brand;
import com.company.intershop.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }

    @Override
    public Brand findById(Long brandId) {
        return brandRepository.findById(brandId);
    }

}
