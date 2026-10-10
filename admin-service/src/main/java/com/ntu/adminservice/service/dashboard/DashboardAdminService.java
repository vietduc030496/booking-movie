package com.ntu.adminservice.service.dashboard;

import com.ntu.moviecore.domain.authentication.repository.UserRepository;
import com.ntu.moviecore.domain.booking.dto.DashboardAdminResponse;
import com.ntu.moviecore.domain.booking.entity.BookingStatus;
import com.ntu.moviecore.domain.booking.repository.BookingRepository;
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
