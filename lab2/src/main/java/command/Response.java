package command;

/**
 * 命令执行结果响应类。
 * <p>
 * 封装命令执行后的结果，包括成功状态、消息和数据。
 * </p>
 *
 * @param <T> 数据类型
 * @author Aaron
 * @version 1.0
 */
public class Response<T> {

    /** 是否成功 */
    private boolean success;

    /** 响应消息 */
    private String message;

    /** 响应数据 */
    private T data;

    /**
     * 构造响应对象。
     *
     * @param success 是否成功
     * @param message 响应消息
     * @param data    响应数据
     */
    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 获取是否成功。
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置是否成功。
     *
     * @param success 是否成功
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取响应消息。
     *
     * @return 响应消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置响应消息。
     *
     * @param message 响应消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取响应数据。
     *
     * @return 响应数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置响应数据。
     *
     * @param data 响应数据
     */
    public void setData(T data) {
        this.data = data;
    }
}
