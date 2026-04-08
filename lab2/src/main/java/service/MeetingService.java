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

/**
 * 会议服务类。
 * <p>
 * 提供会议的增删查等业务逻辑，包括时间冲突检测。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class MeetingService {

    private final MeetingMapper meetingMapper;
    private final UserService userService;

    /**
     * 构造会议服务。
     *
     * @param meetingMapper 会议数据访问对象
     * @param userService   用户服务
     */
    public MeetingService(MeetingMapper meetingMapper, UserService userService) {
        this.meetingMapper = meetingMapper;
        this.userService = userService;
    }

    /**
     * 添加新会议。
     * <p>
     * 会检查被预约用户是否存在，以及发起用户和被预约用户的时间是否有冲突。
     * </p>
     *
     * @param launchUser       发起会议的用户
     * @param scheduledUserName 被预约的用户名
     * @param title            会议标题
     * @param start            开始时间
     * @param end              结束时间
     * @return 新创建的会议ID
     * @throws UserNotFoundException   如果被预约用户不存在
     * @throws MeetingConflictException 如果时间冲突
     */
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

    /**
     * 检查用户在指定时间段是否有会议冲突。
     *
     * @param userName 用户名
     * @param start    开始时间
     * @param end      结束时间
     * @throws MeetingConflictException 如果时间冲突
     */
    private void checkOverlap(String userName, LocalDateTime start, LocalDateTime end) {
        List<Meeting> meetings = meetingMapper.findByUser(userName);
        for (Meeting m : meetings) {
            if (TimeUtil.isOverlap(start, end, m.getStartTime(), m.getEndTime())) {
                throw new MeetingConflictException(userName);
            }
        }
    }

    /**
     * 根据时间范围查询用户参与的会议。
     *
     * @param user  用户
     * @param start 开始时间
     * @param end   结束时间
     * @return 符合条件的会议列表
     */
    public List<Meeting> queryMeetingByTime(User user,
                                            LocalDateTime start, LocalDateTime end) {
        return meetingMapper.findByUserAndTime(user.getName(), start, end);
    }

    /**
     * 清空用户的所有会议。
     *
     * @param user 用户
     */
    public void clearMeeting(User user) {
        meetingMapper.clear(user.getName());
    }

    /**
     * 删除指定会议。
     *
     * @param user      用户
     * @param meetingId 会议ID
     */
    public void deleteMeeting(User user, Integer meetingId) {
        meetingMapper.deleteById(user.getName(), meetingId);
    }
}
