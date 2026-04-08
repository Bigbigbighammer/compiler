package exception;

/**
 * 未知命令异常。
 * <p>
 * 当用户输入未知的命令时抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class UnknownCommandException extends BizException {

    /**
     * 构造未知命令异常。
     *
     * @param command 未知的命令名
     */
    public UnknownCommandException(String command) {
        super("Unknown command: " + command);
    }
}
