package com.company.intershop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String poster;

    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    private float price;

    private ZonedDateTime createdAt;

    public Product(Brand brand, String title, String poster, float price) {
        this.title = title;
        this.poster = poster;
        this.brand = brand;
        this.price = price;
    }

    @PrePersist
    public void onPrePersist() {
        createdAt = ZonedDateTime.now();
    }
}
