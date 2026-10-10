package com.ntu.adminservice.application.dashboard.service;

import com.ntu.persistence.authentication.repository.UserJpaRepository;
import com.ntu.moviecore.application.booking.dto.DashboardAdminResponse;
import com.ntu.moviecore.domain.booking.model.BookingStatus;
import com.ntu.persistence.booking.repository.BookingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardAdminService {

    private static final int LATEST_BOOKINGS_SIZE = 10;

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    /**
     * Revenue only counts confirmed (paid) bookings.
     */
    @Transactional(readOnly = true)
    public DashboardAdminResponse getDashboard() {
        return new DashboardAdminResponse(
                userRepository.count(),
                bookingRepository.count(),
                bookingRepository.sumTotalPriceByStatus(BookingStatus.CONFIRMED),
                bookingRepository.findLatestBookings(PageRequest.of(0, LATEST_BOOKINGS_SIZE)));
    }
}
