package exception;

public class InvalidTimeFormatException extends BizException {
    public InvalidTimeFormatException(String input, String expectedFormat) {
        super("Invalid time format: '" + input + "'. Expected format: " + expectedFormat);
    }
}
