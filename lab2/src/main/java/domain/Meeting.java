package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 会议实体类，表示系统中的一个会议。
 * <p>
 * 会议包含标题、开始时间、结束时间、发起用户和被预约用户。
 * 会议时间不能重叠，发起用户和被预约用户的时间都需要检查冲突。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class Meeting {

    /** 日期时间格式化器，用于 toString 输出 */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** 会议唯一标识 */
    private Integer id;

    /** 会议标题 */
    private String label;

    /** 会议开始时间 */
    private LocalDateTime startTime;

    /** 会议结束时间 */
    private LocalDateTime endTime;

    /** 发起会议的用户名 */
    private String launchUser;

    /** 被预约的用户名 */
    private String scheduledUser;

    /**
     * 默认构造函数。
     */
    public Meeting() {
    }

    /**
     * 带参数的构造函数。
     *
     * @param id            会议ID
     * @param label         会议标题
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param launchUser    发起用户名
     * @param scheduledUser 被预约用户名
     */
    public Meeting(Integer id, String label, LocalDateTime startTime, LocalDateTime endTime, String launchUser, String scheduledUser) {
        this.id = id;
        this.label = label;
        this.startTime = startTime;
        this.endTime = endTime;
        this.launchUser = launchUser;
        this.scheduledUser = scheduledUser;
    }

    /**
     * 获取会议ID。
     *
     * @return 会议ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置会议ID。
     *
     * @param id 会议ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会议标题。
     *
     * @return 会议标题
     */
    public String getLabel() {
        return label;
    }

    /**
     * 设置会议标题。
     *
     * @param label 会议标题
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 获取开始时间。
     *
     * @return 开始时间
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间。
     *
     * @param startTime 开始时间
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间。
     *
     * @return 结束时间
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间。
     *
     * @param endTime 结束时间
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取发起用户名。
     *
     * @return 发起用户名
     */
    public String getLaunchUser() {
        return launchUser;
    }

    /**
     * 设置发起用户名。
     *
     * @param launchUser 发起用户名
     */
    public void setLaunchUser(String launchUser) {
        this.launchUser = launchUser;
    }

    /**
     * 获取被预约用户名。
     *
     * @return 被预约用户名
     */
    public String getScheduledUser() {
        return scheduledUser;
    }

    /**
     * 设置被预约用户名。
     *
     * @param scheduledUser 被预约用户名
     */
    public void setScheduledUser(String scheduledUser) {
        this.scheduledUser = scheduledUser;
    }

    /**
     * 返回会议的字符串表示。
     * <p>
     * 格式：[ID=1] 会议标题 (2024-01-01 10:00 ~ 2024-01-01 11:00) [发起人 -> 被预约人]
     * </p>
     *
     * @return 会议的字符串表示
     */
    @Override
    public String toString() {
        return String.format("[ID=%d] %s (%s ~ %s) [%s -> %s]",
                id, label,
                startTime.format(FORMATTER),
                endTime.format(FORMATTER),
                launchUser, scheduledUser);
    }
}
