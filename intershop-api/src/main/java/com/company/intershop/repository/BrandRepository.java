package com.company.intershop.repository;

import com.company.intershop.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Brand findByName(String name);

    Brand findById(Long id);
}
