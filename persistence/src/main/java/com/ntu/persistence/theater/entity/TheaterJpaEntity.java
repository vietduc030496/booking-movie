package com.ntu.persistence.theater.entity;

import com.ntu.persistence.shared.entity.BaseJpaEntity;
import com.ntu.persistence.material.entity.WardJpaEntity;
import jakarta.persistence.Column;
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
