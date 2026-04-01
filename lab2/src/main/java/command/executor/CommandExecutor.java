package command.executor;

import command.Command;
import command.Response;
import command.enums.CommandType;
import command.parse.CommandParser;
import command.parse.ParsedCommand;
import command.registry.CommandRegistry;
import domain.Meeting;
import exception.BizException;
import ui.IOHandler;

import java.util.List;

public class CommandExecutor {

    private final CommandRegistry registry;
    private final CommandParser commandParser;
    private final IOHandler io;

    public CommandExecutor(CommandRegistry registry, CommandParser commandParser, IOHandler io) {
        this.registry = registry;
        this.commandParser = commandParser;
        this.io = io;
    }

    public void start() {
        io.writeLine("Agenda Service Started. Type 'quit' to exit.");

        while (true) {
            String input = io.readLine();

            try {
                ParsedCommand parsedCommand = commandParser.parse(input);

                Command<?> command = registry.getCommand(parsedCommand.getCommandType());

                if (command == null) {
                    io.writeLine("Unknown command");
                    continue;
                }

                Response<?> response = command.execute(parsedCommand.getArgs());

                if ("QUIT".equals(response.getMessage())) {
                    break;
                }

                printResponse(response);
            } catch (BizException e) {
                io.writeLine(e.getMessage());
            }
        }

        io.writeLine("Goodbye!");
    }

    public String executeLine(String input) {
        try {
            ParsedCommand parsedCommand = commandParser.parse(input);
            Command<?> command = registry.getCommand(parsedCommand.getCommandType());
            if (command == null) {
                return "Unknown command";
            }
            Response<?> response = command.execute(parsedCommand.getArgs());
            return formatResponse(response);
        } catch (BizException e) {
            return e.getMessage();
        }
    }

    private void printResponse(Response<?> response) {
        io.writeLine(response.getMessage());

        if (response.getData() instanceof List<?> list) {
            for (Object item : list) {
                io.writeLine("  " + item.toString());
            }
        } else if (response.getData() != null) {
            io.writeLine(response.getData().toString());
        }
    }

    private String formatResponse(Response<?> response) {
        StringBuilder sb = new StringBuilder(response.getMessage());
        if (response.getData() instanceof List<?> list) {
            for (Object item : list) {
                sb.append("\n  ").append(item.toString());
            }
        } else if (response.getData() != null) {
            sb.append("\n").append(response.getData().toString());
        }
        return sb.toString();
    }
}