package exception;

public class AuthFailedException extends BizException {
    public AuthFailedException() {
        super("Authentication failed: incorrect password");
    }
}

