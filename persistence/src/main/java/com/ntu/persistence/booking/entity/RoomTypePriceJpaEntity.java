package com.ntu.persistence.booking.entity;

import com.ntu.persistence.shared.entity.BaseJpaEntity;
import com.ntu.persistence.theater.entity.RoomTypeJpaEntity;
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
