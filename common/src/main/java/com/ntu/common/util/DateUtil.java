package com.ntu.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    public static String localDateToString(LocalDate date) {
        if (date == null) return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateTimeFormatter);
    }

    public static String localDateTimeToString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(dateTimeFormatter);
    }

    public static String localDateTimeToString(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) return null;
        return dateTime.format(formatter);
    }

    public static String formatVNDay(LocalDateTime time) {
        DayOfWeek day = time.getDayOfWeek();
        int dayValue = day.getValue();

        if (dayValue == 7) {
            return "CN";
        } else {
            return "T" + dayValue;
        }
    }

    public static String formatVNFull(LocalDateTime datetime) {
        String datePart = datetime.format(DateTimeFormatter.ofPattern("dd/MM"));
        String dayText = formatVNDay(datetime);
        return datePart + " - " + dayText;
    }
}
