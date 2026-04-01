package mapper.impl;

import domain.Meeting;
import domain.User;
import mapper.MeetingMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultMeetingMapper implements MeetingMapper {

    private final Map<Integer, Meeting> meetingTable = new HashMap<>();
    private final Map<String, List<Meeting>> userIndex = new HashMap<>();

    @Override
    public List<Meeting> findByUserAndTime(String name, LocalDateTime start, LocalDateTime end) {
        List<Meeting> list = userIndex.getOrDefault(name, List.of());
        return list.stream()
                .filter(meeting -> !meeting.getStartTime().isAfter(end)
                        && !meeting.getEndTime().isBefore(start))
                .toList();
    }

    @Override
    public List<Meeting> findByUser(String name) {
        return userIndex.getOrDefault(name, List.of());
    }

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

    @Override
    public void save(Meeting meeting) {
        meetingTable.put(meeting.getId(), meeting);
        userIndex
                .computeIfAbsent(meeting.getLaunchUser(),k -> new ArrayList<>())
                .add(meeting);
        userIndex
                .computeIfAbsent(meeting.getScheduledUser(),k -> new ArrayList<>())
                .add(meeting);
    }

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
