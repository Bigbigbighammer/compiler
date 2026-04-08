package command.parse;

import command.enums.CommandType;
import exception.EmptyCommandException;
import exception.UnknownCommandException;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单命令解析器实现。
 * <p>
 * 将用户输入字符串按空格分割为命令和参数。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class SimpleCommandParser implements CommandParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public ParsedCommand parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new EmptyCommandException();
        }

        List<String> parts = parseParts(input.trim());

        String commandName = parts.get(0);

        CommandType type = CommandType.from(commandName.toLowerCase());

        if (type == null) {
            throw new UnknownCommandException(commandName);
        }

        String[] args = parts.subList(1, parts.size()).toArray(new String[0]);
        return new ParsedCommand(type, args);
    }

    /**
     * 解析输入字符串为参数列表。
     * <p>
     * 支持空格分隔，最后一个参数可以包含空格。
     * </p>
     *
     * @param input 输入字符串
     * @return 参数列表
     */
    private List<String> parseParts(String input) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (current.length() > 0) {
                    parts.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            parts.add(current.toString());
        }

        return parts;
    }
}
