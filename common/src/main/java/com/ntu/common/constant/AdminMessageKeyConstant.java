package com.ntu.common.constant;

/**
 * i18n keys of the messages shown on admin pages (see messages_*.properties).
 */
public final class AdminMessageKeyConstant {

    private AdminMessageKeyConstant() {
    }

    public static final String SAVE_SUCCESS = "admin.save.success";
    public static final String DELETE_SUCCESS = "admin.delete.success";
    public static final String INVALID_INPUT = "admin.invalid.input";

    public static final String SHOWTIME_OVERLAP = "admin.showtime.overlap";
    public static final String SHOWTIME_INVALID_TIME_RANGE = "admin.showtime.invalid_time_range";
    public static final String SHOWTIME_HAS_BOOKINGS = "admin.showtime.has_bookings";

    public static final String ROOM_NAME_DUPLICATED = "admin.room.name_duplicated";
}
