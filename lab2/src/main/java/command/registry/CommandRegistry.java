package command.registry;

import command.Command;
import command.enums.CommandType;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<CommandType, Command<?>> commandMap = new HashMap<>();

    public void registerCommand(CommandType commandType, Command<?> command) {
        commandMap.put(commandType, command);
    }

    public Command<?> getCommand(CommandType commandType) {
        return commandMap.get(commandType);
    }

}
