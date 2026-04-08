package command.executor;

import command.Command;
import command.Response;
import command.enums.CommandType;
import command.parse.CommandParser;
import command.parse.ParsedCommand;
import command.registry.CommandRegistry;
import exception.BizException;
import ui.IOHandler;

import java.util.List;

/**
 * 命令执行器。
 * <p>
 * 负责接收用户输入，解析命令，执行并输出结果。
 * 支持交互式模式和单行执行模式。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class CommandExecutor {

    private final CommandRegistry registry;
    private final CommandParser commandParser;
    private final IOHandler io;

    /**
     * 构造命令执行器。
     *
     * @param registry      命令注册表
     * @param commandParser 命令解析器
     * @param io            输入输出处理器
     */
    public CommandExecutor(CommandRegistry registry, CommandParser commandParser, IOHandler io) {
        this.registry = registry;
        this.commandParser = commandParser;
        this.io = io;
    }

    /**
     * 启动交互式命令循环。
     * <p>
     * 持续读取用户输入，解析并执行命令，直到收到 quit 命令。
     * </p>
     */
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

    /**
     * 执行单行命令。
     * <p>
     * 用于批量命令执行或远程调用。
     * </p>
     *
     * @param input 命令字符串
     * @return 执行结果字符串
     */
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

    /**
     * 输出响应到 IOHandler。
     *
     * @param response 响应对象
     */
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

    /**
     * 格式化响应为字符串。
     *
     * @param response 响应对象
     * @return 格式化后的字符串
     */
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
