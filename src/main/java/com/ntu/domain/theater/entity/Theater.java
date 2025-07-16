package com.ntu.domain.theater.entity;

import com.ntu.domain.base.entity.BaseEntity;
import com.ntu.domain.material.entity.Ward;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_theater")
@Getter
@Setter
public class Theater extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    private String phone;

}
