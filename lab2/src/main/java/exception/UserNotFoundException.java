package exception;

/**
 * 用户未找到异常。
 * <p>
 * 当尝试操作一个不存在的用户时抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class UserNotFoundException extends BizException {

    /**
     * 构造用户未找到异常。
     *
     * @param userName 未找到的用户名
     */
    public UserNotFoundException(String userName) {
        super("User not found: " + userName);
    }
}
