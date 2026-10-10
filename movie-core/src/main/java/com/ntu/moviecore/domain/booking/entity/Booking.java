package com.ntu.moviecore.domain.booking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ntu.moviecore.domain.authentication.entity.User;
import com.ntu.moviecore.domain.base.entity.BaseEntity;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import com.ntu.moviecore.domain.theater.entity.Seat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_booking")
@Getter
@Setter
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "booking_seats",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // HELD, BOOKED, EXPIRED, CANCELLED

    private LocalDateTime expireTime;

}
