package com.ntu.moviecore.domain.booking.entity;

import com.ntu.moviecore.domain.base.entity.BaseEntity;
import com.ntu.moviecore.domain.theater.entity.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_room_type_price")
@Getter
@Setter
public class RoomTypePrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    private float price;
}
