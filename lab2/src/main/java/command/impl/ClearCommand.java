package command.impl;

import command.Command;
import command.Response;
import domain.User;
import exception.InvalidArgumentException;
import service.MeetingService;
import service.UserService;

/**
 * 清空会议命令。
 * <p>
 * 格式：{@code clear <username> <password>}
 * </p>
 * <p>
 * 清空指定用户参与的所有会议。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class ClearCommand implements Command<Void> {

    private final UserService userService;
    private final MeetingService meetingService;

    /**
     * 构造清空会议命令。
     *
     * @param userService   用户服务
     * @param meetingService 会议服务
     */
    public ClearCommand(UserService userService, MeetingService meetingService) {
        this.userService = userService;
        this.meetingService = meetingService;
    }

    /**
     * {@inheritDoc}
     */
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
