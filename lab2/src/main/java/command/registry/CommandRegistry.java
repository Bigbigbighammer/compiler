package command.registry;

import command.Command;
import command.enums.CommandType;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令注册表。
 * <p>
 * 用于注册和获取命令实例，维护命令类型与命令实例的映射关系。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class CommandRegistry {

    /** 命令类型与命令实例的映射 */
    private final Map<CommandType, Command<?>> commandMap = new HashMap<>();

    /**
     * 注册命令。
     *
     * @param commandType 命令类型
     * @param command     命令实例
     */
    public void registerCommand(CommandType commandType, Command<?> command) {
        commandMap.put(commandType, command);
    }

    /**
     * 获取命令实例。
     *
     * @param commandType 命令类型
     * @return 命令实例，未注册则返回 null
     */
    public Command<?> getCommand(CommandType commandType) {
        return commandMap.get(commandType);
    }
}
