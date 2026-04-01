package exception;

public class UserAlreadyExistsException extends BizException{
    public UserAlreadyExistsException(String userName) {
        super("User already exists: " + userName);
    }
}
