package cloud7c.speedrunrecorder.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static final String TIME_FORMAT = "%02d:%02d";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter Date_TIME_WITHOUT_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");

    public static String formatTime(long milliseconds){
        long seconds = milliseconds / 1000 % 60;
        long minutes = milliseconds / 60_000;
        return String.format(TIME_FORMAT, minutes, seconds);
    }

    public static String formatDateTime(long milliseconds) {
        Instant instant = Instant.ofEpochMilli(milliseconds);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTime.format(Date_TIME_WITHOUT_YEAR_FORMATTER);
    }

    public static String formatDateTime(long milliseconds, boolean withYear) {
        if(!withYear){
            return formatDateTime(milliseconds);
        }
        Instant instant = Instant.ofEpochMilli(milliseconds);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTime.format(DATE_TIME_FORMATTER);
    }
}
