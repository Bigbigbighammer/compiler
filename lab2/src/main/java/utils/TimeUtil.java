package utils;

import exception.InvalidTimeFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 时间工具类。
 * <p>
 * 提供时间解析和时间段重叠判断等功能。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class TimeUtil {

    /** 时间格式字符串，用于用户输入输出 */
    public static final String TIME_FORMAT = "yyyy-MM-dd-HH:mm";

    /** 时间格式化器 */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(TIME_FORMAT);

    /**
     * 解析时间字符串。
     * <p>
     * 时间格式为 {@code yyyy-MM-dd-HH:mm}，例如 {@code 2024-01-01-10:00}。
     * </p>
     *
     * @param str 时间字符串
     * @return 解析后的 LocalDateTime 对象
     * @throws InvalidTimeFormatException 如果时间格式不正确
     */
    public static LocalDateTime parse(String str) {
        try {
            return LocalDateTime.parse(str, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormatException(str, TIME_FORMAT);
        }
    }

    /**
     * 判断两个时间段是否重叠。
     *
     * @param s1 第一个时间段的开始时间
     * @param e1 第一个时间段的结束时间
     * @param s2 第二个时间段的开始时间
     * @param e2 第二个时间段的结束时间
     * @return 如果重叠返回 true，否则返回 false
     */
    public static boolean isOverlap(
            LocalDateTime s1, LocalDateTime e1,
            LocalDateTime s2, LocalDateTime e2
    ) {
        return !(e1.isBefore(s2) || s1.isAfter(e2));
    }
}
