package com.ntu.moviecore.domain.theater.entity;

import com.ntu.moviecore.domain.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_room_type")
@Getter
@Setter
public class RoomType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
