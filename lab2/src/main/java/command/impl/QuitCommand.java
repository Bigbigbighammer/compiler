package command.impl;

import command.Command;
import command.Response;

/**
 * 退出命令。
 * <p>
 * 格式：{@code quit}
 * </p>
 * <p>
 * 终止程序运行。
 * </p>
 *
 * @author Aaron
 * @version 1.0
 */
public class QuitCommand implements Command<Void> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<Void> execute(String[] args) {
        return new Response<>(true, "QUIT", null);
    }
}
