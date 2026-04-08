package ui;

/**
 * 输入输出处理器接口。
 * <p>
 * 定义了读取用户输入和输出消息的标准接口，
 * 支持不同的实现方式（控制台、Socket等）。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public interface IOHandler {

    /**
     * 读取一行输入。
     *
     * @return 用户输入的一行字符串，如果到达末尾则返回 null
     */
    String readLine();

    /**
     * 输出一行消息。
     *
     * @param message 要输出的消息
     */
    void writeLine(String message);
}
