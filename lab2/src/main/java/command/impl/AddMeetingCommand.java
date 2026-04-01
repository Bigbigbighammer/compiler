package command.impl;

import command.Command;
import command.Response;
import domain.User;
import exception.InvalidArgumentException;
import service.MeetingService;
import service.UserService;
import utils.TimeUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AddMeetingCommand implements Command<Void> {

    private final UserService userService;
    private final MeetingService meetingService;

    public AddMeetingCommand(UserService userService, MeetingService meetingService) {
        this.userService = userService;
        this.meetingService = meetingService;
    }

    @Override
    public Response<Void> execute(String[] args) {
        if (args.length < 6) {
            throw new InvalidArgumentException("add requires at least 6 arguments: <username> <password> <other> <start> <end> <title>\nTime format: " + TimeUtil.TIME_FORMAT);
        }
        String userName = args[0];
        String password = args[1];
        String other = args[2];
        LocalDateTime start = TimeUtil.parse(args[3]);
        LocalDateTime end = TimeUtil.parse(args[4]);
        if (start.isAfter(end)) {
            throw new InvalidArgumentException("Start time must be before end time");
        }
        String title = String.join(" ", Arrays.copyOfRange(args, 5, args.length));
        User user = userService.authorize(userName, password);
        int meetingId = meetingService.addMeeting(user, other, title, start, end);
        return new Response<>(true, "Meeting created successfully. Meeting ID: " + meetingId, null);
    }
}
