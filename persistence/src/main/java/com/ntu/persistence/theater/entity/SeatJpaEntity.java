package com.ntu.persistence.theater.entity;

import com.ntu.persistence.shared.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
