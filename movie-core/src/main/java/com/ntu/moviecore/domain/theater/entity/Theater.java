package com.ntu.moviecore.domain.theater.entity;

import com.ntu.moviecore.domain.base.entity.BaseEntity;
import com.ntu.moviecore.domain.material.entity.Ward;
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

    private String hotline;

    @Column(name = "is_default", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDefault;
}
