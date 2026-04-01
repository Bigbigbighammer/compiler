package command.impl;

import command.Command;
import command.Response;
import exception.InvalidArgumentException;
import service.UserService;

public class RegisterCommand implements Command<Void> {

    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

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
