package mapper.impl;

import domain.Meeting;
import mapper.MeetingMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议数据访问的默认实现。
 * <p>
 * 使用内存中的 HashMap 存储会议数据，同时维护用户索引以便快速查询。
 * 适用于单机演示，可替换为数据库实现。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class DefaultMeetingMapper implements MeetingMapper {

    /** 会议表，键为会议ID，值为会议对象 */
    private final Map<Integer, Meeting> meetingTable = new HashMap<>();

    /** 用户索引，键为用户名，值为该用户参与的所有会议列表 */
    private final Map<String, List<Meeting>> userIndex = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> findByUserAndTime(String name, LocalDateTime start, LocalDateTime end) {
        List<Meeting> list = userIndex.getOrDefault(name, List.of());
        return list.stream()
                .filter(meeting -> !meeting.getStartTime().isAfter(end)
                        && !meeting.getEndTime().isBefore(start))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> findByUser(String name) {
        return userIndex.getOrDefault(name, List.of());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(String name, Integer meetingId) {
        Meeting meeting = meetingTable.remove(meetingId);
        if (meeting == null) {
            return;
        }
        userIndex
                .getOrDefault(meeting.getLaunchUser(), List.of())
                .remove(meeting);
        userIndex
                .getOrDefault(meeting.getScheduledUser(), List.of())
                .remove(meeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Meeting meeting) {
        meetingTable.put(meeting.getId(), meeting);
        userIndex
                .computeIfAbsent(meeting.getLaunchUser(), k -> new ArrayList<>())
                .add(meeting);
        userIndex
                .computeIfAbsent(meeting.getScheduledUser(), k -> new ArrayList<>())
                .add(meeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear(String name) {
        List<Meeting> list = userIndex.getOrDefault(name, List.of());
        List<Meeting> toDelete = new ArrayList<>(list);
        for (Meeting meeting : toDelete) {
            meetingTable.remove(meeting.getId());
            userIndex.getOrDefault(meeting.getLaunchUser(), List.of()).remove(meeting);
            userIndex.getOrDefault(meeting.getScheduledUser(), List.of()).remove(meeting);
        }
    }
}
