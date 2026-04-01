package exception;

public class MeetingConflictException extends BizException {
    public MeetingConflictException(String userName) {
        super("Meeting time conflict for user: " + userName);
    }
}
