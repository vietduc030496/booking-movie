package com.ntu.domain.theater.entity;

import com.ntu.domain.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_seat")
@Getter
@Setter
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "col_symbol", nullable = false)
    private String colSymbol;

    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @ManyToOne
    @JoinColumn(name = "seat_type_id")
    private SeatType seatType;
}
