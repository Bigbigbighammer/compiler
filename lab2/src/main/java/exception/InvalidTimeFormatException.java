package exception;

/**
 * 时间格式无效异常。
 * <p>
 * 当用户输入的时间格式不符合要求时抛出此异常。
 * 正确的时间格式为 {@code yyyy-MM-dd-HH:mm}。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class InvalidTimeFormatException extends BizException {

    /**
     * 构造时间格式无效异常。
     *
     * @param input          用户输入的错误时间字符串
     * @param expectedFormat 期望的时间格式
     */
    public InvalidTimeFormatException(String input, String expectedFormat) {
        super("Invalid time format: '" + input + "'. Expected format: " + expectedFormat);
    }
}
