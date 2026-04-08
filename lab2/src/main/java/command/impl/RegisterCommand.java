package command.impl;

import command.Command;
import command.Response;
import exception.InvalidArgumentException;
import service.UserService;

/**
 * 注册用户命令。
 * <p>
 * 格式：{@code register <username> <password>}
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class RegisterCommand implements Command<Void> {

    private final UserService userService;

    /**
     * 构造注册命令。
     *
     * @param userService 用户服务
     */
    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<Void> execute(String[] args) {
        if (args.length != 2) {
            throw new InvalidArgumentException("register requires 2 arguments: <username> <password>");
        }
        String userName = args[0];
        String password = args[1];
        userService.addUser(userName, password);
        return new Response<>(true, "User registered successfully: " + userName, null);
    }
}
