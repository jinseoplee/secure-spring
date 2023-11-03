package com.ljs.entity;

import com.ljs.entity.enums.Currency;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
