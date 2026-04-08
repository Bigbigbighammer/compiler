package utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ID 生成器工具类。
 * <p>
 * 使用原子整数生成唯一ID，保证线程安全。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class IdGenerator {

    /** 会议ID计数器，从1开始递增 */
    private static final AtomicInteger MEETING_ID = new AtomicInteger(1);

    /**
     * 生成下一个会议ID。
     *
     * @return 新的会议ID
     */
    public static int nextMeetingId() {
        return MEETING_ID.getAndIncrement();
    }
}
