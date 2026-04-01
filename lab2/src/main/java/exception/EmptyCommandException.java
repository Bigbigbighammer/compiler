package exception;

public class EmptyCommandException extends BizException {
    public EmptyCommandException() {
        super("Empty command");
    }
}
