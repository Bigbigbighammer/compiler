package mapper;

import domain.Meeting;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 会议数据访问接口。
 * <p>
 * 提供会议的持久化操作，包括保存、查询、删除等。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public interface MeetingMapper {

    /**
     * 根据用户名和时间范围查询会议。
     * <p>
     * 返回该用户在该时间范围内参与的所有会议（包括发起和被预约）。
     * </p>
     *
     * @param name  用户名
     * @param start 开始时间
     * @param end   结束时间
     * @return 符合条件的会议列表
     */
    List<Meeting> findByUserAndTime(String name, LocalDateTime start, LocalDateTime end);

    /**
     * 根据用户名查询所有会议。
     *
     * @param name 用户名
     * @return 该用户参与的所有会议列表
     */
    List<Meeting> findByUser(String name);

    /**
     * 根据会议ID删除会议。
     *
     * @param name      操作用户名（用于权限验证）
     * @param meetingId 会议ID
     */
    void deleteById(String name, Integer meetingId);

    /**
     * 保存会议。
     *
     * @param meeting 要保存的会议对象
     */
    void save(Meeting meeting);

    /**
     * 清空用户的所有会议。
     *
     * @param name 用户名
     */
    void clear(String name);
}
