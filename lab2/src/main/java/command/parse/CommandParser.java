package command.parse;

/**
 * 命令解析器接口。
 * <p>
 * 定义将用户输入字符串解析为命令对象的标准接口。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public interface CommandParser {

    /**
     * 解析用户输入。
     *
     * @param input 用户输入字符串
     * @return 解析后的命令对象
     * @throws exception.EmptyCommandException   如果输入为空
     * @throws exception.UnknownCommandException 如果命令未知
     */
    ParsedCommand parse(String input);
}
