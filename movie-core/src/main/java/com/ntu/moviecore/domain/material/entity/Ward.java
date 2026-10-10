package com.ntu.moviecore.domain.material.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mt_ward")
@Getter
@Setter
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String wardCode;

    private String name;

    @ManyToOne
    @JoinColumn(name = "province_code", referencedColumnName = "province_code")
    private Province province;
}
