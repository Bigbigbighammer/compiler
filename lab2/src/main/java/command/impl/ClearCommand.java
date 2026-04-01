package command.impl;

import command.Command;
import command.Response;
import domain.User;
import exception.InvalidArgumentException;
import service.MeetingService;
import service.UserService;

public class ClearCommand implements Command<Void> {

    private final UserService userService;
    private final MeetingService meetingService;

    public ClearCommand(UserService userService, MeetingService meetingService) {
        this.userService = userService;
        this.meetingService = meetingService;
    }

    @Override
    public Response<Void> execute(String[] args) {
        if (args.length != 2) {
            throw new InvalidArgumentException("clear requires 2 arguments: <username> <password>");
        }
        String userName = args[0];
        String password = args[1];
        User user = userService.authorize(userName, password);
        meetingService.clearMeeting(user);
        return new Response<>(true, "All meetings cleared successfully", null);
    }
}
