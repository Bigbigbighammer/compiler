package exception;

/**
 * 业务异常基类。
 * <p>
 * 所有业务相关的异常都应继承此类，以便统一处理。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class BizException extends RuntimeException {

    /**
     * 构造业务异常。
     *
     * @param message 异常消息
     */
    public BizException(String message) {
        super(message);
    }
}
