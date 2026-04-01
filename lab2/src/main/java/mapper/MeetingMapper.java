package mapper;

import domain.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingMapper {

    List<Meeting> findByUserAndTime(String name, LocalDateTime start, LocalDateTime end);

    List<Meeting> findByUser(String name);

    void deleteById(String name, Integer meetingId);

    void save(Meeting meeting);

    void clear(String name);

}
