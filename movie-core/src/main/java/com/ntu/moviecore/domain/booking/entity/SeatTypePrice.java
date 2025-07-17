package com.ntu.moviecore.domain.booking.entity;

import com.ntu.moviecore.domain.base.entity.BaseEntity;
import com.ntu.moviecore.domain.theater.entity.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_seat_type_price")
@Getter
@Setter
public class SeatTypePrice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

    private float price;
}
