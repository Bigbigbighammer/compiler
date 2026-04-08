package exception;

/**
 * 会议时间冲突异常。
 * <p>
 * 当创建的会议与用户已有的会议时间重叠时抛出此异常。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class MeetingConflictException extends BizException {

    /**
     * 构造会议时间冲突异常。
     *
     * @param userName 发生时间冲突的用户名
     */
    public MeetingConflictException(String userName) {
        super("Meeting time conflict for user: " + userName);
    }
}
