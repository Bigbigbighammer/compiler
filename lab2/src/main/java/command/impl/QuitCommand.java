package command.impl;

import command.Command;
import command.Response;

public class QuitCommand implements Command<Void> {

    @Override
    public Response<Void> execute(String[] args) {
        return new Response<>(true, "QUIT", null);
    }
}
