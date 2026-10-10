package com.ntu.moviecore.domain.booking.repository;

import com.ntu.moviecore.domain.booking.dto.BookingAdminResponse;
import com.ntu.moviecore.domain.booking.entity.Booking;
import com.ntu.moviecore.domain.booking.entity.BookingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByShowtimeId(Long showtimeId);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status = :status")
    long sumTotalPriceByStatus(@Param("status") BookingStatus status);

    @Query("SELECT new com.ntu.moviecore.domain.booking.dto.BookingAdminResponse(" +
            "b.id, o.email, m.title, b.createdAt, b.status, b.totalPrice) " +
            "FROM Booking b " +
            "LEFT JOIN b.owner o " +
            "LEFT JOIN b.showtime s " +
            "LEFT JOIN s.movie m " +
            "ORDER BY b.createdAt DESC")
    List<BookingAdminResponse> findLatestBookings(Pageable pageable);
}
