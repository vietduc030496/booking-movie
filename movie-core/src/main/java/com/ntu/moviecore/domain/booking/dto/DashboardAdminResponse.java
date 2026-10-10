package com.ntu.moviecore.domain.booking.dto;

import java.util.List;

public record DashboardAdminResponse(long totalUsers,
                                     long totalBookings,
                                     long revenue,
                                     List<BookingAdminResponse> latestBookings) {
}
