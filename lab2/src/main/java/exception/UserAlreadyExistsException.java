package exception;

/**
 * 用户已存在异常。
 * <p>
 * 当尝试注册一个已存在的用户名时抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class UserAlreadyExistsException extends BizException {

    /**
     * 构造用户已存在异常。
     *
     * @param userName 已存在的用户名
     */
    public UserAlreadyExistsException(String userName) {
        super("User already exists: " + userName);
    }
}
