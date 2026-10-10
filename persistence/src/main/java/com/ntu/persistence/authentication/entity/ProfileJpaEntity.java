package com.ntu.persistence.authentication.entity;

import com.ntu.persistence.authentication.entity.converter.GenderConverter;
import com.ntu.persistence.shared.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_profile")
@Getter
@Setter
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    private String city;

    private String district;

    private String address;

    @Convert(converter = GenderConverter.class)
    private Gender gender;

    private String avatarUrl;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private User user;

}
