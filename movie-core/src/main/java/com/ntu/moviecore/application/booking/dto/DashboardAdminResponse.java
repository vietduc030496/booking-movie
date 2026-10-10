package com.ntu.moviecore.application.booking.dto;

import java.util.List;

public record DashboardAdminResponse(long totalUsers,
                                     long totalBookings,
                                     long revenue,
                                     List<BookingAdminResponse> latestBookings) {
}
