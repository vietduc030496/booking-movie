package com.ntu.domain.booking;

import com.ntu.domain.base.entity.BaseEntity;
import com.ntu.domain.theater.entity.SeatType;
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
