package service;

import domain.Meeting;
import domain.User;
import exception.MeetingConflictException;
import exception.UserNotFoundException;
import mapper.MeetingMapper;
import utils.IdGenerator;
import utils.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingService {

    private final MeetingMapper meetingMapper;
    private final UserService userService;

    public MeetingService(MeetingMapper meetingMapper, UserService userService) {
        this.meetingMapper = meetingMapper;
        this.userService = userService;
    }

    public int addMeeting(User launchUser,
                           String scheduledUserName, String title,
                           LocalDateTime start, LocalDateTime end) {
        if (userService.findUser(scheduledUserName) == null) {
            throw new UserNotFoundException(scheduledUserName);
        }

        // Check overlap for launch user
        checkOverlap(launchUser.getName(), start, end);

        // Check overlap for scheduled user
        checkOverlap(scheduledUserName, start, end);

        int id = IdGenerator.nextMeetingId();
        Meeting meeting = new Meeting(id, title, start, end, launchUser.getName(), scheduledUserName);
        meetingMapper.save(meeting);
        return id;
    }

    private void checkOverlap(String userName, LocalDateTime start, LocalDateTime end) {
        List<Meeting> meetings = meetingMapper.findByUser(userName);
        for (Meeting m : meetings) {
            if (TimeUtil.isOverlap(start, end, m.getStartTime(), m.getEndTime())) {
                throw new MeetingConflictException(userName);
            }
        }
    }

    public List<Meeting> queryMeetingByTime(User user,
                                            LocalDateTime start, LocalDateTime end) {
        return meetingMapper.findByUserAndTime(user.getName(), start, end);
    }

    public void clearMeeting(User user) {
        meetingMapper.clear(user.getName());
    }

    public void deleteMeeting(User user, Integer meetingId) {
        meetingMapper.deleteById(user.getName(), meetingId);
    }
}
