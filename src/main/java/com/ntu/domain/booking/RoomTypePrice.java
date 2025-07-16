package com.ntu.domain.booking;

import com.ntu.domain.base.entity.BaseEntity;
import com.ntu.domain.theater.entity.RoomType;
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

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    private float price;
}
