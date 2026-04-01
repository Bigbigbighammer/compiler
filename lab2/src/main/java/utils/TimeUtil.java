package utils;

import exception.InvalidTimeFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtil {

    public static final String TIME_FORMAT = "yyyy-MM-dd-HH:mm";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static LocalDateTime parse(String str) {
        try {
            return LocalDateTime.parse(str, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormatException(str, TIME_FORMAT);
        }
    }

    public static boolean isOverlap(
            LocalDateTime s1, LocalDateTime e1,
            LocalDateTime s2, LocalDateTime e2
    ) {
        return !(e1.isBefore(s2) || s1.isAfter(e2));
    }
}