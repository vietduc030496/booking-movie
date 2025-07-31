package com.ntu.moviecore.domain.authentication.entity;

import com.ntu.moviecore.domain.authentication.entity.converter.GenderConverter;
import com.ntu.moviecore.domain.base.entity.BaseEntity;
import jakarta.persistence.*;
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
