package command;

/**
 * 命令接口。
 * <p>
 * 所有命令都实现此接口，通过 {@link #execute(String[])} 方法执行命令逻辑。
 * </p>
 *
 * @param <T> 返回数据的类型
 * @author Aaron
 * @version 1.0
 */
public interface Command<T> {

    /**
     * 执行命令。
     *
     * @param args 命令参数
     * @return 命令执行结果
     */
    Response<T> execute(String[] args);
}
