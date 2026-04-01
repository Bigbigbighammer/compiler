package command.enums;

public enum CommandType {

    REGISTER("register"),
    ADD("add"),
    QUERY("query"),
    DELETE("delete"),
    CLEAR("clear"),
    BATCH("batch"),
    QUIT("quit");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static CommandType from(String input) {
        for (CommandType type : values()) {
            if (type.command.equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }
}
