package command.enums;

/**
 * 命令类型枚举。
 * <p>
 * 定义系统支持的所有命令类型。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public enum CommandType {

    /** 注册用户命令 */
    REGISTER("register"),

    /** 添加会议命令 */
    ADD("add"),

    /** 查询会议命令 */
    QUERY("query"),

    /** 删除会议命令 */
    DELETE("delete"),

    /** 清空会议命令 */
    CLEAR("clear"),

    /** 批量执行命令 */
    BATCH("batch"),

    /** 退出程序命令 */
    QUIT("quit");

    /** 命令字符串 */
    private final String command;

    /**
     * 构造命令类型。
     *
     * @param command 命令字符串
     */
    CommandType(String command) {
        this.command = command;
    }

    /**
     * 获取命令字符串。
     *
     * @return 命令字符串
     */
    public String getCommand() {
        return command;
    }

    /**
     * 根据输入字符串获取对应的命令类型。
     *
     * @param input 输入字符串
     * @return 命令类型，未找到则返回 null
     */
    public static CommandType from(String input) {
        for (CommandType type : values()) {
            if (type.command.equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }
}
