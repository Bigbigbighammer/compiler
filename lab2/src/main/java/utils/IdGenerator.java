package utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final AtomicInteger MEETING_ID = new AtomicInteger(1);

    public static int nextMeetingId() {
        return MEETING_ID.getAndIncrement();
    }
}