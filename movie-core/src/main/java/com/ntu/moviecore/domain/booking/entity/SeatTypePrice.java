package com.ntu.moviecore.domain.booking.entity;

import com.ntu.moviecore.domain.base.entity.BaseEntity;
import com.ntu.moviecore.domain.theater.entity.SeatType;
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
@Table(name = "tb_seat_type_price")
@Getter
@Setter
public class SeatTypePrice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;

    private float price;
}
