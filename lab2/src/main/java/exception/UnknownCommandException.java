package exception;

public class UnknownCommandException extends BizException {
    public UnknownCommandException(String command) {
        super("Unknown command: " + command);
    }
}
