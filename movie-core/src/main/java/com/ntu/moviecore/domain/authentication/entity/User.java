package com.ntu.moviecore.domain.authentication.entity;

import com.ntu.moviecore.domain.authentication.entity.converter.GenderConverter;
import com.ntu.moviecore.domain.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String city;

    private String district;

    private String address;

    @Convert(converter = GenderConverter.class)
    private Gender gender;

    private String avatarUrl;

}
