package com.ntu.moviecore.domain.material.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "province")
    private List<Ward> wards;
}
