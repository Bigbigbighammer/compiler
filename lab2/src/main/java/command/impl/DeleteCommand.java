package command.impl;

import command.Command;
import command.Response;
import domain.User;
import exception.InvalidArgumentException;
import service.MeetingService;
import service.UserService;

public class DeleteCommand implements Command<Void> {

    private final UserService userService;
    private final MeetingService meetingService;

    public DeleteCommand(UserService userService, MeetingService meetingService) {
        this.userService = userService;
        this.meetingService = meetingService;
    }

    @Override
    public Response<Void> execute(String[] args) {
        if (args.length != 3) {
            throw new InvalidArgumentException("delete requires 3 arguments: <username> <password> <meetingId>");
        }
        String userName = args[0];
        String password = args[1];
        int meetingId;
        try {
            meetingId = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("meetingId must be a valid integer");
        }
        User user = userService.authorize(userName, password);
        meetingService.deleteMeeting(user, meetingId);
        return new Response<>(true, "Meeting deleted successfully", null);
    }
}
