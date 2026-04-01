package exception;

public class UserNotFoundException extends BizException {
    public UserNotFoundException(String userName) {
        super("User not found: " + userName);
    }
}
