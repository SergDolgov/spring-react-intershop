package com.company.intershop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "brands", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Brand(String name) {
        this.name = name;
    }
}
