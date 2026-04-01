package command.parse;

import command.enums.CommandType;

public class ParsedCommand {

    CommandType commandType;
    String[] args;

    public ParsedCommand(CommandType commandType, String[] args) {
        this.commandType = commandType;
        this.args = args;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
