package exception;

/**
 * 参数无效异常。
 * <p>
 * 当命令参数数量或格式不正确时抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class InvalidArgumentException extends BizException {

    /**
     * 构造参数无效异常。
     *
     * @param message 错误消息，描述参数的正确格式
     */
    public InvalidArgumentException(String message) {
        super(message);
    }
}
