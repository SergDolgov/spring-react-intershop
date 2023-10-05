package com.company.intershop.repository;

import com.company.intershop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAllByOrderByTitle();

    List<Product> findByTitleContainingIgnoreCaseOrderByTitle(String title);

    List<Product> findByIdIn(List<Long> Ids);
}
