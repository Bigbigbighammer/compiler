package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Meeting {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Integer id;
    private String label;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String launchUser;
    private String scheduledUser;

    public Meeting() {
    }

    public Meeting(Integer id, String label, LocalDateTime startTime, LocalDateTime endTime, String launchUser, String scheduledUser) {
        this.id = id;
        this.label = label;
        this.startTime = startTime;
        this.endTime = endTime;
        this.launchUser = launchUser;
        this.scheduledUser = scheduledUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLaunchUser() {
        return launchUser;
    }

    public void setLaunchUser(String launchUser) {
        this.launchUser = launchUser;
    }

    public String getScheduledUser() {
        return scheduledUser;
    }

    public void setScheduledUser(String scheduledUser) {
        this.scheduledUser = scheduledUser;
    }

    @Override
    public String toString() {
        return String.format("[ID=%d] %s (%s ~ %s) [%s -> %s]",
                id, label,
                startTime.format(FORMATTER),
                endTime.format(FORMATTER),
                launchUser, scheduledUser);
    }
}
