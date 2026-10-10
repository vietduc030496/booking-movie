package com.ntu.moviecore.application.booking.dto;

import com.ntu.moviecore.domain.booking.model.BookingStatus;

import java.time.LocalDateTime;

public record BookingAdminResponse(Long bookingId,
                                   String userEmail,
                                   String movieTitle,
                                   LocalDateTime bookedAt,
                                   BookingStatus status,
                                   int totalPrice) {
}
