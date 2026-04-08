package command.parse;

import command.enums.CommandType;

/**
 * 解析后的命令对象。
 * <p>
 * 包含命令类型和参数数组。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class ParsedCommand {

    /** 命令类型 */
    CommandType commandType;

    /** 命令参数 */
    String[] args;

    /**
     * 构造解析后的命令对象。
     *
     * @param commandType 命令类型
     * @param args        命令参数
     */
    public ParsedCommand(CommandType commandType, String[] args) {
        this.commandType = commandType;
        this.args = args;
    }

    /**
     * 获取命令类型。
     *
     * @return 命令类型
     */
    public CommandType getCommandType() {
        return commandType;
    }

    /**
     * 设置命令类型。
     *
     * @param commandType 命令类型
     */
    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    /**
     * 获取命令参数。
     *
     * @return 命令参数数组
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * 设置命令参数。
     *
     * @param args 命令参数数组
     */
    public void setArgs(String[] args) {
        this.args = args;
    }
}
