package command.parse;

import command.enums.CommandType;
import exception.EmptyCommandException;
import exception.UnknownCommandException;

import java.util.Arrays;

public class SimpleCommandParser implements CommandParser{
    @Override
    public ParsedCommand parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new EmptyCommandException();
        }

        String[] parts = input.trim().split("\\s+");

        String commandName = parts[0];

        CommandType type = CommandType.from(commandName.toLowerCase());

        if (type == null) {
            throw new UnknownCommandException(commandName);
        }

        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        return new ParsedCommand(type, args);
    }
}
