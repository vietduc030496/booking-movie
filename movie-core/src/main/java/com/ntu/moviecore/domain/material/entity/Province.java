package com.ntu.moviecore.domain.material.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mt_province")
@Getter
@Setter
public class Province {
    @Id
    private Integer id;

    @Column(name = "province_code", unique = true, nullable = false)
    private String provinceCode;

    private String name;

    private String code;

    @Column(name = "place_type")
    private String placeType;
}
