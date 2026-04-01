package command.impl;

import command.Command;
import command.Response;
import domain.Meeting;
import domain.User;
import exception.InvalidArgumentException;
import service.MeetingService;
import service.UserService;
import utils.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;

public class QueryMeetingCommand implements Command<List<Meeting>> {

    private final UserService userService;
    private final MeetingService meetingService;

    public QueryMeetingCommand(UserService userService, MeetingService meetingService) {
        this.userService = userService;
        this.meetingService = meetingService;
    }

    @Override
    public Response<List<Meeting>> execute(String[] args) {
        if (args.length != 4) {
            throw new InvalidArgumentException("query requires 4 arguments: <username> <password> <start> <end>\nTime format: " + TimeUtil.TIME_FORMAT);
        }
        String userName = args[0];
        String password = args[1];
        LocalDateTime start = TimeUtil.parse(args[2]);
        LocalDateTime end = TimeUtil.parse(args[3]);
        if (start.isAfter(end)) {
            throw new InvalidArgumentException("Start time must be before end time");
        }
        User user = userService.authorize(userName, password);
        List<Meeting> meetings = meetingService.queryMeetingByTime(user, start, end);
        String message = "Found " + meetings.size() + " meeting(s) between " + start + " and " + end + ":";
        return new Response<>(true, message, meetings);
    }
}
