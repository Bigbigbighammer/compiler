package exception;

/**
 * 认证失败异常。
 * <p>
 * 当用户登录时密码错误抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class AuthFailedException extends BizException {

    /**
     * 构造认证失败异常。
     */
    public AuthFailedException() {
        super("Authentication failed: incorrect password");
    }
}
