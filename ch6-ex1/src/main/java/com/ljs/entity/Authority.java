package com.ljs.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @JoinColumn(name = "user")
    @ManyToOne
    private User user;
}
