package exception;

/**
 * 空命令异常。
 * <p>
 * 当用户输入空命令时抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class EmptyCommandException extends BizException {

    /**
     * 构造空命令异常。
     */
    public EmptyCommandException() {
        super("Empty command");
    }
}
